package com.octo.gwt.test.internal.utils;

import javassist.CtClass;
import javassist.NotFoundException;

import com.octo.gwt.test.internal.GwtClassPool;

public class PropertyContainerUtils {

	public static CtClass STRING_TYPE;

	static {
		try {
			STRING_TYPE = GwtClassPool.get().get(String.class.getName());
		} catch (NotFoundException e) {
			// Never append
			throw new RuntimeException(e);
		}
	}

	private PropertyContainerUtils() {

	}

	public static String getConstructionCode() {
		return "new " + PropertyContainer.class.getName() + "(this)";
	}

	public static void setProperty(Object o, String propertyName, Object propertyValue) {
		PropertyContainerAware pca = cast(o);
		if (pca == null) {
			throw new RuntimeException("Call setProperty on null object");
		}
		pca.getProperties().put(propertyName, propertyValue);
	}

	public static void setProperty(Object o, String propertyName, short propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
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
		return PropertyContainerUtils.class.getCanonicalName() + ".setProperty(" + object + ", \"" + propertyName + "\", " + propertyValue + ")";
	}

	public static PropertyContainerAware cast(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof PropertyContainerAware) {
			PropertyContainerAware pca = (PropertyContainerAware) o;
			return pca;
		} else {
			throw new RuntimeException("Not " + PropertyContainerAware.class.getSimpleName() + " for getProperty on object : " + o);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getProperty(Object o, String propertyName) {
		return (T) cast(o).getProperties().get(propertyName);
	}

	public static short getPropertyShort(Object o, String propertyName) {
		return cast(o).getProperties().getShort(propertyName);
	}

	public static int getPropertyInteger(Object o, String propertyName) {
		return cast(o).getProperties().getInteger(propertyName);
	}

	public static boolean getPropertyBoolean(Object o, String propertyName) {
		return cast(o).getProperties().getBoolean(propertyName);
	}

	public static double getPropertyDouble(Object o, String propertyName) {
		return cast(o).getProperties().getDouble(propertyName);
	}

	public static String getPropertyString(Object o, String propertyName) {
		return cast(o).getProperties().getString(propertyName);
	}

	public static String getCodeGetProperty(String object, String fieldName, CtClass returnType) {
		if (returnType == CtClass.booleanType) {
			return PropertyContainerUtils.class.getCanonicalName() + ".getPropertyBoolean(" + object + ", \"" + fieldName + "\")";
		} else if (returnType == CtClass.intType) {
			return PropertyContainerUtils.class.getCanonicalName() + ".getPropertyInteger(" + object + ", \"" + fieldName + "\")";
		} else if (returnType == CtClass.doubleType) {
			return PropertyContainerUtils.class.getCanonicalName() + ".getPropertyDouble(" + object + ", \"" + fieldName + "\")";
		} else if (returnType == STRING_TYPE) {
			return PropertyContainerUtils.class.getCanonicalName() + ".getPropertyString(" + object + ", \"" + fieldName + "\")";
		}
		return "(" + returnType.getName() + ") " + PropertyContainerUtils.class.getCanonicalName() + ".getProperty(" + object + ", \"" + fieldName
				+ "\")";
	}

}
