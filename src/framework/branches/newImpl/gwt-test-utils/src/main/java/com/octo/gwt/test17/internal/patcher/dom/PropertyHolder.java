package com.octo.gwt.test17.internal.patcher.dom;

import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;
import javassist.CtPrimitiveType;

import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.ElementWrapper;

public class PropertyHolder {

	private static final Map<Object, Map<String, Object>> CACHE = new HashMap<Object, Map<String, Object>>();

	public static Map<String, Object> get(Object o) {
		if (o instanceof ElementWrapper) {
			o = ((ElementWrapper) o).getWrappedElement();
		}
		Map<String, Object> result = CACHE.get(o);
		if (result == null) {
			result = new HashMap<String, Object>();
			// init with DOM properties
			if (o instanceof Element) {
				result.put("AccessKey", "");
			}
			CACHE.put(o, result);
		}

		return result;
	}

	public static void set(Object o, Map<String, Object> map) {
		CACHE.put(o, map);
	}

	public static void clear() {
		CACHE.clear();
	}

	public static void clearObject(Object o) {
		CACHE.remove(o);
	}

	public static String callGet(String propertyName, CtClass returnType) {
		if (returnType.isPrimitive()) {
			CtPrimitiveType type = (CtPrimitiveType) returnType;
			String defaultValue;
			if (type.getWrapperName().equals(Boolean.class.getCanonicalName())) {
				defaultValue = "false";
			} else {
				defaultValue = "0";
			}
			StringBuilder sb = new StringBuilder();
			sb.append("Object result = " + PropertyHolder.class.getCanonicalName() + ".get(this).get(\"" + propertyName + "\");");
			sb.append("if (result == null) { return " + defaultValue + "; }");
			sb.append("return ($r) result;");
			return sb.toString();
		} else {
			return "return ($r) " + PropertyHolder.class.getCanonicalName() + ".get(this).get(\"" + propertyName + "\");";
		}
	}

	public static String callSet(String propertyName, String value) {
		return PropertyHolder.class.getCanonicalName() + ".get(this).put(\"" + propertyName + "\", " + value + ");";
	}

}
