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
import java.util.List;

public class GwtTestReflectionUtils {

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

	public static List<Field> getAnnotatedField(Class<?> clazz, Class<?> annotationClass) {
		List<Field> l = new ArrayList<Field>();
		recurseGetAnnotatedField(l, clazz, annotationClass);
		return l;
	}

	public static List<Field> findFieldByName(Class<?> clazz, String fieldName) {
		List<Field> list = new ArrayList<Field>();
		for (Field f : clazz.getFields()) {
			if (f.getName().equals(fieldName)) {
				list.add(f);
			}
		}
		for (Field f : clazz.getDeclaredFields()) {
			if (f.getName().equals(fieldName)) {
				list.add(f);
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			list.addAll(findFieldByName(superClazz, fieldName));
		}

		return list;
	}

	private static Field getUniqueFieldByName(Class<?> clazz, String fieldName) {
		List<Field> list = findFieldByName(clazz, fieldName);
		if (list.size() == 0) {
			throw new RuntimeException("Unable to find field, class " + clazz + ", fieldName " + fieldName);
		}
		if (list.size() > 1) {
			throw new RuntimeException("Unable to choose field, " + clazz + ", fieldName " + fieldName);
		}
		Field field = list.iterator().next();
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
