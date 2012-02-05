package com.octo.gwt.test.internal.utils;

import javassist.CtClass;
import javassist.NotFoundException;

import com.octo.gwt.test.internal.GwtClassPool;

public class PropertyContainerHelper {

	public static CtClass STRING_TYPE;

	static {
		try {
			STRING_TYPE = GwtClassPool.get().get(String.class.getName());
		} catch (NotFoundException e) {
			// Never append
			throw new RuntimeException(e);
		}
	}

	public static PropertyContainerAware cast(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof PropertyContainerAware) {
			PropertyContainerAware pca = (PropertyContainerAware) o;
			return pca;
		} else {
			throw new RuntimeException("Not "
					+ PropertyContainerAware.class.getSimpleName()
					+ " for getProperty on object : " + o);
		}
	}

	public static String getCodeGetProperty(String object, String fieldName,
			CtClass returnType) {
		if (returnType == CtClass.booleanType) {
			return PropertyContainerHelper.class.getCanonicalName()
					+ ".getPropertyBoolean(" + object + ", \"" + fieldName
					+ "\")";
		} else if (returnType == CtClass.intType) {
			return PropertyContainerHelper.class.getCanonicalName()
					+ ".getPropertyInteger(" + object + ", \"" + fieldName
					+ "\")";
		} else if (returnType == CtClass.doubleType) {
			return PropertyContainerHelper.class.getCanonicalName()
					+ ".getPropertyDouble(" + object + ", \"" + fieldName
					+ "\")";
		} else if (returnType == STRING_TYPE) {
			return PropertyContainerHelper.class.getCanonicalName()
					+ ".getPropertyString(" + object + ", \"" + fieldName
					+ "\")";
		}
		return "(" + returnType.getName() + ") "
				+ PropertyContainerHelper.class.getCanonicalName()
				+ ".getProperty(" + object + ", \"" + fieldName + "\")";
	}

	public static String getCodeSetProperty(String object, String propertyName,
			String propertyValue) {
		return PropertyContainerHelper.class.getCanonicalName()
				+ ".setProperty(" + object + ", \"" + propertyName + "\", "
				+ propertyValue + ")";
	}

	public static String getConstructionCode() {
		return "new " + PropertyContainer.class.getName() + "(this)";
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObject(Object o, String propertyName) {
		return (T) cast(o).getProperties().get(propertyName);
	}

	public static boolean getBoolean(Object o, String propertyName) {
		return cast(o).getProperties().getBoolean(propertyName);
	}

	public static double getDouble(Object o, String propertyName) {
		return cast(o).getProperties().getDouble(propertyName);
	}

	public static int getInteger(Object o, String propertyName) {
		return cast(o).getProperties().getInteger(propertyName);
	}

	public static short getShort(Object o, String propertyName) {
		return cast(o).getProperties().getShort(propertyName);
	}

	public static String getString(Object o, String propertyName) {
		return cast(o).getProperties().getString(propertyName);
	}

	public static void setProperty(Object o, String propertyName,
			boolean propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static void setProperty(Object o, String propertyName,
			double propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static void setProperty(Object o, String propertyName,
			int propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static void setProperty(Object o, String propertyName,
			Object propertyValue) {
		PropertyContainerAware pca = cast(o);
		if (pca == null) {
			throw new RuntimeException("Call setProperty on null object");
		}
		pca.getProperties().put(propertyName, propertyValue);
	}

	public static void setProperty(Object o, String propertyName,
			short propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

}
