package com.octo.gwt.test.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GwtTestReflectionUtils {

	private static DoubleMap<Class<?>, Class<?>, Object> cacheAnnotation = new DoubleMap<Class<?>, Class<?>, Object>();
	private static DoubleMap<Class<?>, Class<?>, Set<Field>> cacheAnnotatedField = new DoubleMap<Class<?>, Class<?>, Set<Field>>();
	private static DoubleMap<Class<?>, Class<?>, Map<Method, ?>> cacheAnnotatedMethod = new DoubleMap<Class<?>, Class<?>, Map<Method, ?>>();

	@SuppressWarnings("unchecked")
	public static <T> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {

		Object o = cacheAnnotation.get(clazz, annotationClass);
		if (o != null) {
			return (T) o;
		}

		T result = null;
		for (Annotation a : clazz.getDeclaredAnnotations()) {
			if (a.annotationType() == annotationClass) {
				result = (T) a;
			}
		}
		if (result == null && clazz.getSuperclass() != null) {
			result = getAnnotation(clazz.getSuperclass(), annotationClass);
		}

		cacheAnnotation.put(clazz, annotationClass, result);

		return result;
	}

	public static Method findMethod(Class<?> clazz, String name, Class<?>[] paramTypes) {
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Annotation> void recurseGetAnnotatedMethod(Map<Method, T> map, Class<?> target, Class<?> annotationClass) {
		for (Method m : target.getDeclaredMethods()) {
			for (Annotation a : m.getDeclaredAnnotations()) {
				if (a.annotationType() == annotationClass) {
					map.put(m, (T) a);
				}
			}
		}
		if (target.getSuperclass() != null) {
			recurseGetAnnotatedMethod(map, target.getSuperclass(), annotationClass);
		}
	}

	private static void recurseGetAnnotatedField(Set<Field> set, Class<?> target, Class<?> annotationClass) {
		for (Field f : target.getDeclaredFields()) {
			for (Annotation a : f.getDeclaredAnnotations()) {
				if (a.annotationType() == annotationClass) {
					set.add(f);
				}
			}
		}
		if (target.getSuperclass() != null) {
			recurseGetAnnotatedField(set, target.getSuperclass(), annotationClass);
		}
	}

	public static Set<Field> getAnnotatedField(Class<?> clazz, Class<?> annotationClass) {
		Set<Field> l = cacheAnnotatedField.get(clazz, annotationClass);
		if (l != null) {
			return l;
		}
		l = new HashSet<Field>();
		recurseGetAnnotatedField(l, clazz, annotationClass);
		cacheAnnotatedField.put(clazz, annotationClass, l);
		return l;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> Map<Method, T> getAnnotatedMethod(Class<?> target, Class<T> annotationClass) {
		Map<Method, T> map = (Map<Method, T>) cacheAnnotatedMethod.get(target, annotationClass);
		if (map != null) {
			return map;
		}
		map = new HashMap<Method, T>();
		recurseGetAnnotatedMethod(map, target, annotationClass);
		cacheAnnotatedMethod.put(target, annotationClass, map);
		return map;
	}

	private static DoubleMap<Class<?>, String, Set<Field>> cacheField = new DoubleMap<Class<?>, String, Set<Field>>();

	public static Set<Field> findFieldByName(Class<?> clazz, String fieldName) {
		Set<Field> set = cacheField.get(clazz, fieldName);
		if (set != null) {
			return set;
		}
		set = new HashSet<Field>();
		;
		for (Field f : clazz.getFields()) {
			if (f.getName().equals(fieldName)) {
				set.add(f);
			}
		}
		for (Field f : clazz.getDeclaredFields()) {
			if (f.getName().equals(fieldName)) {
				set.add(f);
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			set.addAll(findFieldByName(superClazz, fieldName));
		}

		return set;
	}

	private static DoubleMap<Class<?>, String, Field> cacheUniqueField = new DoubleMap<Class<?>, String, Field>();

	private static Field getUniqueFieldByName(Class<?> clazz, String fieldName) {
		Field f = cacheUniqueField.get(clazz, fieldName);
		if (f != null) {
			return f;
		}
		Set<Field> set = findFieldByName(clazz, fieldName);
		if (set.size() == 0) {
			throw new RuntimeException("Unable to find field, class " + clazz + ", fieldName " + fieldName);
		}
		if (set.size() > 1) {
			throw new RuntimeException("Unable to choose field, " + clazz + ", fieldName " + fieldName);
		}
		Field field = set.iterator().next();
		field.setAccessible(true);
		cacheUniqueField.put(clazz, fieldName, field);
		return field;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getPrivateFieldValue(Object target, String fieldName) {
		Field field = getUniqueFieldByName(target.getClass(), fieldName);
		try {
			return (T) field.get(target);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + " Unable to get field, class " + fieldName + ", fieldClass " + target.getClass());
		}
	}

	public static void setPrivateFieldValue(Object target, String fieldName, Object value) {
		Field field = getUniqueFieldByName(target.getClass(), fieldName);
		try {
			field.set(target, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + " Unable to set field, class " + fieldName + ", fieldClass " + target.getClass());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getStaticFieldValue(Class<?> clazz, String fieldName) {
		Field field = getUniqueFieldByName(clazz, fieldName);
		try {
			return (T) field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + " Unable to get static field, class " + fieldName + ", fieldClass " + clazz);
		}
	}

	public static void setStaticField(Class<?> clazz, String fieldName, Object value) {
		Field field = getUniqueFieldByName(clazz, fieldName);
		try {
			field.set(null, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + " Unable to set field, class " + fieldName + ", fieldClass " + clazz);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T callPrivateMethod(Object target, String methodName, Object... args) {
		Class<?>[] l = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			l[i] = args[i].getClass();
		}
		try {
			Method m = target.getClass().getDeclaredMethod(methodName, l);
			m.setAccessible(true);
			Object res = m.invoke(target, args);
			return (T) res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to call method \"" + target.getClass().getSimpleName() + "." + methodName + "\"", e);
		}
	}

	public static void getStaticAndCallClear(Class<?> clazz, String fieldName) {
		callPrivateMethod(getStaticFieldValue(clazz, fieldName), "clear");
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
			return (T) ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException("Unable to serialize / unserialize object " + o.getClass().getCanonicalName(), e);
		}
	}

	/**
	 * Action to take on each method.
	 */
	public static interface MethodCallback {

		/**
		 * Perform an operation using the given method.
		 * 
		 * @param method
		 *            the method to operate on
		 */
		void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
	}

	public static void doWithMethods(Class<?> targetClass, MethodCallback mc) throws IllegalArgumentException {
		// Keep backing up the inheritance hierarchy.
		do {
			Method[] methods = targetClass.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				try {
					mc.doWith(methods[i]);
				} catch (IllegalAccessException ex) {
					throw new IllegalStateException("Shouldn't be illegal to access method '" + methods[i].getName() + "': " + ex);
				}
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null);
	}

}
