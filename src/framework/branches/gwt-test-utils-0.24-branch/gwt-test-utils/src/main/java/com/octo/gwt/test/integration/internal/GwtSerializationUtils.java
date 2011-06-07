package com.octo.gwt.test.integration.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.octo.gwt.test.utils.GwtReflectionUtils;

public abstract class GwtSerializationUtils {

	@SuppressWarnings("unchecked")
	public static <T> T serializeUnserialize(Object o) {
		return (T) serializeUnserialize(o, null, null);
	}

	@SuppressWarnings("unchecked")
	public static <T> T serializeUnserialize(Object o, ClassLoader classLoader, DeserializationContext callbacks) {
		if (o == null) {
			return null;
		}
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new CustomObjectInputStream(bis, classLoader, callbacks);
			T clone = (T) ois.readObject();
			treatTransientRecursive(clone, new HashMap<Class<?>, Object>(), new HashSet<Object>());

			return clone;
		} catch (Exception e) {
			throw new RuntimeException("Unable to serialize / unserialize object " + o.getClass().getCanonicalName(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private static void treatTransientRecursive(Object clone, Map<Class<?>, Object> prototypes, Set<Object> cache) throws IllegalArgumentException,
			IllegalAccessException {
		if (clone == null) {
			return;
		} else if (clone.getClass().getName().startsWith("java.") && !clone.getClass().getName().contains("$")) {
			return;
		} else if (isPrimitive(clone.getClass()) || clone.getClass().isEnum() || CharSequence.class.isInstance(clone)) {
			return;
		} else if (cache.contains(clone)) {
			return;
		} else if (clone.getClass().isArray()) {
			Class<?> arrayType = clone.getClass().getComponentType();
			if (isPrimitive(arrayType)) {
				return;
			}
			Object[] oldArray = (Object[]) clone;
			for (int i = 0; i < oldArray.length; i++) {
				treatTransientRecursive(oldArray[i], prototypes, cache);
			}
		} else if (Map.class.isInstance(clone)) {
			for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) clone).entrySet()) {
				treatTransientRecursive(entry.getKey(), prototypes, cache);
				treatTransientRecursive(entry.getValue(), prototypes, cache);
			}
		} else if (Collection.class.isInstance(clone)) {
			for (Object entry : (Collection<Object>) clone) {
				treatTransientRecursive(entry, prototypes, cache);
			}
		} else {
			// Setup in cache to avoid cyclic recursion
			cache.add(clone);

			//callPrivateMethod(clone, SerializableClassModifier.DEFAULT_CONSTRUCTOR_COPY_FOR_SERIALIZATION);
			for (Field f : GwtReflectionUtils.getFields(clone.getClass())) {
				GwtReflectionUtils.makeAccessible(f);
				Object fieldValue = f.get(clone);
				if (!Modifier.isTransient(f.getModifiers())) {
					treatTransientRecursive(fieldValue, prototypes, cache);
				} else if (fieldValue == null) {
					// Gwt Serialization callback could have initialized transient field by themselves
					Object prototype = prototypes.get(clone.getClass());
					if (prototype == null) {
						prototype = GwtReflectionUtils.instantiateClass(clone.getClass());
						prototypes.put(clone.getClass(), prototype);
					}

					f.set(clone, f.get(prototype));
				}
			}
		}
	}

	private static boolean isPrimitive(Class<?> clazz) {
		return clazz.isPrimitive() || clazz == Boolean.class || clazz == Character.class || clazz == Byte.class || clazz == Short.class
				|| clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class || clazz == Void.class;
	}

	static class CustomObjectInputStream extends ObjectInputStream {

		private ClassLoader classLoader;

		private DeserializationContext callbacks;

		public CustomObjectInputStream(InputStream in, ClassLoader classLoader, DeserializationContext callbacks) throws IOException {
			super(in);
			enableResolveObject(true);
			this.classLoader = classLoader;
			this.callbacks = callbacks;
		}

		@Override
		protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
			return classLoader == null ? super.resolveClass(desc) : Class.forName(desc.getName(), true, classLoader);
		}

		@Override
		protected Object resolveObject(Object obj) throws IOException {
			if (callbacks == null) {
				return super.resolveObject(obj);
			}
			try {
				return callbacks.resolve(obj);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

}
