package com.octo.gwt.test.patcher;

import javassist.CtClass;

import com.octo.gwt.test.internal.GwtTestClassLoader;

public class PropertyContainerHelper {

	public static void setProperty(Object o, String propertyName, Object propertyValue) {
		setProperty(o, propertyName, propertyValue, false);
	}

	public static void setProperty(Object o, String propertyName, Object propertyValue, boolean allowNull) {
		PropertyContainerAware pca = cast(o);
		if (pca == null) {
			if (allowNull) {
				return;
			} else {
				throw new RuntimeException("Call setProperty on null object");
			}
		}
		pca.getProperties().put(propertyName, propertyValue);
	}

	public static void setProperty(Object o, String propertyName, short propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static void setProperty(Object o, String propertyName, short propertyValue, boolean allowNull) {
		setProperty(o, propertyName, (Object) propertyValue, allowNull);
	}

	public static void setProperty(Object o, String propertyName, int propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static void setProperty(Object o, String propertyName, int propertyValue, boolean allowNull) {
		setProperty(o, propertyName, (Object) propertyValue, allowNull);
	}

	public static void setProperty(Object o, String propertyName, boolean propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static void setProperty(Object o, String propertyName, boolean propertyValue, boolean allowNull) {
		setProperty(o, propertyName, (Object) propertyValue, allowNull);
	}

	public static void setProperty(Object o, String propertyName, double propertyValue) {
		setProperty(o, propertyName, (Object) propertyValue);
	}

	public static void setProperty(Object o, String propertyName, double propertyValue, boolean allowNull) {
		setProperty(o, propertyName, (Object) propertyValue, allowNull);
	}

	public static String getCodeSetProperty(String object, String propertyName, String propertyValue, boolean allowNull) {
		return PropertyContainerHelper.class.getCanonicalName() + ".setProperty(" + object + ", \"" + propertyName + "\", " + propertyValue + ", "
				+ Boolean.toString(allowNull) + ")";
	}

	public static PropertyContainerAware cast(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof PropertyContainerAware) {
			PropertyContainerAware pca = (PropertyContainerAware) o;
			return pca;
		} else {
			throw new RuntimeException("Not " + PropertyContainerAware.class.getSimpleName() + " for getProperty " + o.getClass());
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

	public static String getCodeGetProperty(String object, String fieldName, CtClass returnType) {
		if (returnType == CtClass.booleanType) {
			return PropertyContainerHelper.class.getCanonicalName() + ".getPropertyBoolean(" + object + ", \"" + fieldName + "\")";
		} else if (returnType == CtClass.intType) {
			return PropertyContainerHelper.class.getCanonicalName() + ".getPropertyInteger(" + object + ", \"" + fieldName + "\")";
		} else if (returnType == CtClass.doubleType) {
			return PropertyContainerHelper.class.getCanonicalName() + ".getPropertyDouble(" + object + ", \"" + fieldName + "\")";
		}
		return "(" + returnType.getName() + ") " + PropertyContainerHelper.class.getCanonicalName() + ".getProperty(" + object + ", \"" + fieldName
				+ "\")";
	}

	public static Class<?> getPropertyContainerAware(String className) {
		try {
			return GwtTestClassLoader.getInstance().loadClass(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

}
