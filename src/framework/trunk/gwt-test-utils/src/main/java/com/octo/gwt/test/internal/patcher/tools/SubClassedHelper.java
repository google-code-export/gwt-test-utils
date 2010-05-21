package com.octo.gwt.test.internal.patcher.tools;

import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;

import com.octo.gwt.test.ElementWrapper;

public class SubClassedHelper {
	
	private static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
	
	public static void setProperty(Object o, String propertyName, Object propertyValue) {
		setProperty(o, propertyName, propertyValue, false);
	}
	
	public static void setProperty(Object o, String propertyName, Object propertyValue, boolean allowNull) {
		SubClassedObject subClassedObject = getSubClassedObjectOrNull(o);
		if (subClassedObject == null) {
			if (allowNull) {
				return;
			}
			else {
				throw new RuntimeException("Call setProperty on null object");
			}
		}
		subClassedObject.getOverrideProperties().put(propertyName, propertyValue);
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
		return SubClassedHelper.class.getCanonicalName() + ".setProperty(" + object + ", \"" + propertyName + "\", " + propertyValue + ", " + Boolean.toString(allowNull) + ")";
	}
	
	public static SubClassedObject getSubClassedObjectOrNull(Object o) {
		if (o instanceof ElementWrapper) {
			o = ((ElementWrapper) o).getWrappedElement();
		}
		if (o == null) {
			return null;
		}
		if (o instanceof SubClassedObject) {
			SubClassedObject subClassedObject = (SubClassedObject) o;
			return subClassedObject;
		}
		else {
			throw new RuntimeException("Not SubClassedObject for getProperty " + o.getClass());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getProperty(Object o, String propertyName) {
		return (T) getSubClassedObjectOrNull(o).getOverrideProperties().get(propertyName);
	}
	
	public static int getPropertyInteger(Object o, String propertyName) {
		return getSubClassedObjectOrNull(o).getOverrideProperties().getInteger(propertyName);
	}
	
	public static boolean getPropertyBoolean(Object o, String propertyName) {
		return getSubClassedObjectOrNull(o).getOverrideProperties().getBoolean(propertyName);
	}
	
	public static double getPropertyDouble(Object o, String propertyName) {
		return getSubClassedObjectOrNull(o).getOverrideProperties().getDouble(propertyName);
	}
	
	public static String getCodeGetProperty(String object, String fieldName, CtClass returnType) {
		if (returnType == CtClass.booleanType) {
			return SubClassedHelper.class.getCanonicalName() + ".getPropertyBoolean(" + object + ", \"" + fieldName +"\")";
		}
		else if (returnType == CtClass.intType) {
			return SubClassedHelper.class.getCanonicalName() + ".getPropertyInteger(" + object + ", \"" + fieldName +"\")";
		}
		else if (returnType == CtClass.doubleType) {
			return SubClassedHelper.class.getCanonicalName() + ".getPropertyDouble(" + object + ", \"" + fieldName +"\")";
		}
		return "(" + returnType.getName() + ") " + SubClassedHelper.class.getCanonicalName() + ".getProperty(" + object + ", \"" + fieldName +"\")";
	}

	public static void register(Class<?> clazz, Class<?> subClazz) {
		map.put(clazz, subClazz);
	}
	
	public static Class<?> getSubClass(Class<?> clazz) {
		return map.get(clazz);
	}
	
}
