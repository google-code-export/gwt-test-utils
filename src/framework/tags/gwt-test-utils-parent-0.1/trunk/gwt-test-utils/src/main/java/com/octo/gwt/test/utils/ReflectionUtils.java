package com.octo.gwt.test.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReflectionUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
		for (Annotation a : clazz.getDeclaredAnnotations()) {
			if (a.annotationType() == annotationClass) {
				return (T) a;
			}
		}
		if (clazz.getSuperclass() != null) {
			return getAnnotation(clazz.getSuperclass(), annotationClass);
		}
		return null;
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied
	 * name and parameter types. Searches all superclasses up to
	 * <code>Object</code>.
	 * <p>
	 * Returns <code>null</code> if no {@link Method} can be found.
	 * 
	 * @param clazz
	 *            the class to introspect
	 * @param name
	 *            the name of the method
	 * @param paramTypes
	 *            the parameter types of the method (may be <code>null</code> to
	 *            indicate any signature)
	 * @return the Method object, or <code>null</code> if none found
	 */
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

	/**
	 * Perform the given callback operation on all matching methods of the given
	 * class and superclasses.
	 * <p>
	 * The same named method occurring on subclass and superclass will appear
	 * twice, unless excluded by a {@link MethodFilter}.
	 * 
	 * @param targetClass
	 *            class to start looking at
	 * @param mc
	 *            the callback to invoke for each method
	 * @see #doWithMethods(Class, MethodCallback, MethodFilter)
	 */
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

	private static void recurseGetAnnotatedField(List<Field> list, Class<?> target, Class<?> annotationClass) {
		for (Field f : target.getDeclaredFields()) {
			for (Annotation a : f.getDeclaredAnnotations()) {
				if (a.annotationType() == annotationClass) {
					list.add(f);
				}
			}
		}
		if (target.getSuperclass() != null) {
			recurseGetAnnotatedField(list, target.getSuperclass(), annotationClass);
		}
	}

	public static List<Field> getAnnotatedField(Object target, Class<?> annotationClass) {
		List<Field> l = new ArrayList<Field>();
		recurseGetAnnotatedField(l, target.getClass(), annotationClass);
		return l;
	}

	public static Set<Field> findFieldByName(Class<?> clazz, String fieldName) {
		Set<Field> set = new HashSet<Field>();
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

	private static Field getUniqueFieldByName(Class<?> clazz, String fieldName) {
		Set<Field> set = findFieldByName(clazz, fieldName);
		if (set.size() == 0) {
			throw new RuntimeException("Unable to find field, class " + clazz + ", fieldName " + fieldName);
		}
		if (set.size() > 1) {
			throw new RuntimeException("Unable to choose field, " + clazz + ", fieldName " + fieldName);
		}
		Field field = set.iterator().next();
		field.setAccessible(true);
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

	public static void callClear(Object o) {
		try {
			if (o != null) {
				Method m = o.getClass().getDeclaredMethod("clear");
				m.invoke(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + " Unable to call clear method on class " + o.getClass());
		}
	}

	public static void getStaticAndCallClear(Class<?> clazz, String fieldName) {
		callClear(getStaticFieldValue(clazz, fieldName));
	}

	@SuppressWarnings("unchecked")
	public static <T> T serializeUnserialize(Object o) {
		if (o == null) {
			return null;
		}
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			return (T) ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException("Unable to serialize / unserialize object " + o.getClass().getCanonicalName(), e);
		}
	}

	public static void setPrivateField(Object target, String fieldName, Object value) {
		Field field = getUniqueFieldByName(target.getClass(), fieldName);
		try {
			field.set(target, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + " Unable to set field, class " + fieldName + ", fieldClass " + target.getClass());
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

}
