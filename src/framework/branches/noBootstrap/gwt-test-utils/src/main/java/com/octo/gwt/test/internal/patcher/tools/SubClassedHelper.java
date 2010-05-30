package com.octo.gwt.test.internal.patcher.tools;

import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import com.octo.gwt.test.ElementWrapper;
import com.octo.gwt.test.PatchGwtClassPool;

public class SubClassedHelper {
		
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

	private static Map<String, Class<?>> compiledMap = new HashMap<String, Class<?>>();
	
	public static Class<?> getSubClass(String className) {
		Class<?> subClazz = compiledMap.get(className);
		if (subClazz != null) {
			return subClazz;
		}
		try {
			ClassPool pool = PatchGwtClassPool.get();
			String subClassName = className + AutomaticSubclasser.SUB_CLASSED;
			CtClass ctClass = pool.get(subClassName);
			subClazz = ctClass.toClass();
			compiledMap.put(className, subClazz);
			return subClazz;
		}
		catch(NotFoundException e) {
			return null;
		}
		catch(CannotCompileException e) {
			throw new RuntimeException(e);
		}
	}
	
}
