package com.octo.gwt.test.internal.patcher.tools;

import javassist.CtClass;

public class PropertyContainerAwareHelper {

	public static void setProperty(Object o, String propertyName, Object propertyValue) {
		getPropertyContainer(o).put(propertyName, propertyValue);
	}

	public static void setProperty(Object o, String propertyName, int propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static void setProperty(Object o, String propertyName, boolean propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static void setProperty(Object o, String propertyName, double propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static String getCodeSetProperty(String object, String propertyName, String propertyValue) {
		return PropertyContainerAwareHelper.class.getCanonicalName() + ".setProperty(" + object + ", \"" + propertyName + "\", " + propertyValue + ")";
	}
	
	public static PropertyContainer getPropertyContainer(Object o) {
		if (o == null) {
			throw new IllegalArgumentException("Object to get a " + PropertyContainer.class.getSimpleName() + " instance should not be null");
		}
		
		if (o instanceof PropertyContainerAware) {
			return ((PropertyContainerAware) o).getPropertyContainer();
		} else {
			throw new IllegalArgumentException("Not an instance of " + PropertyContainerAware.class.getSimpleName() + " : " + o.getClass());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getProperty(Object o, String propertyName) {
		return (T) getPropertyContainer(o).get(propertyName);
	}

	public static int getPropertyInteger(Object o, String propertyName) {
		return getPropertyContainer(o).getInteger(propertyName);
	}

	public static boolean getPropertyBoolean(Object o, String propertyName) {
		return getPropertyContainer(o).getBoolean(propertyName);
	}

	public static double getPropertyDouble(Object o, String propertyName) {
		return getPropertyContainer(o).getDouble(propertyName);
	}

	public static String getCodeGetProperty(String object, String fieldName, CtClass returnType) {
		if (returnType == CtClass.booleanType) {
			return PropertyContainerAwareHelper.class.getCanonicalName() + ".getPropertyBoolean(" + object + ", \"" + fieldName + "\")";
		} else if (returnType == CtClass.intType) {
			return PropertyContainerAwareHelper.class.getCanonicalName() + ".getPropertyInteger(" + object + ", \"" + fieldName + "\")";
		} else if (returnType == CtClass.doubleType) {
			return PropertyContainerAwareHelper.class.getCanonicalName() + ".getPropertyDouble(" + object + ", \"" + fieldName + "\")";
		}
		return "(" + returnType.getName() + ") " + PropertyContainerAwareHelper.class.getCanonicalName() + ".getProperty(" + object + ", \""
				+ fieldName + "\")";
	}

}
