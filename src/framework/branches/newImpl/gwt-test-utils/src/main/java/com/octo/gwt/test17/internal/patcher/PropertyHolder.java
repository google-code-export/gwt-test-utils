package com.octo.gwt.test17.internal.patcher;

import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;
import javassist.CtPrimitiveType;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NodeFactory;

public class PropertyHolder {

	private static Document DOCUMENT = NodeFactory.createDocument();

	private static final Map<Object, Map<String, Object>> CACHE = new HashMap<Object, Map<String, Object>>();

	public static Map<String, Object> get(Object o) {
		Map<String, Object> result = CACHE.get(o);
		if (result == null) {
			result = new HashMap<String, Object>();
			CACHE.put(o, result);
		}

		return result;
	}

	static Document getDocument() {
		return DOCUMENT;
	}

	public static void set(Object o, Map<String, Object> map) {
		CACHE.put(o, map);
	}

	public static void clear() {
		CACHE.clear();
		DOCUMENT = NodeFactory.createDocument();
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
