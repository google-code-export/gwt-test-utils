package com.octo.gwt.test17;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
	
}
