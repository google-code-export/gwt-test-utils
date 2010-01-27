package com.octo.gwt.test17.internal.patcher.dom;

import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;
import javassist.CtPrimitiveType;

import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.ElementWrapper;
import com.octo.gwt.test17.ng.SubClassedObject;

public class PropertyHolder {

	static class Toto extends HashMap<String, Object> {
		
		private Object o;
		
		public Toto(Object o) {
			this.o = o;
		}

		@Override
		public Object get(Object key) {
			// TODO Auto-generated method stub
			return super.get(key);
		}

		@Override
		public Object put(String key, Object value) {
			// TODO Auto-generated method stub
			return super.put(key, value);
		}
		
	}
	
	private static final Map<Object, Map<String, Object>> CACHE = new HashMap<Object, Map<String, Object>>();

	public static Map<String, Object> get(Object o) {
		if (o instanceof ElementWrapper) {
			o = ((ElementWrapper) o).getWrappedElement();
		}
		if (o != null) {
			if (o instanceof SubClassedObject) {
				
			}
			else {
				System.out.println("Not subclassed object " + o.getClass());
			}
			if (o instanceof Element) {
				
			}
			else {
				System.err.println("Not element object " + o.getClass());
			}
		}
		Map<String, Object> result = CACHE.get(o);
		if (result == null) {
			result = new Toto(o);
			// init with DOM properties
			if (o instanceof Element) {
				result.put("AccessKey", "");
				result.put("ClassName", "");
			}
			CACHE.put(o, result);
		}

		return result;
	}

	public static Object getPropertValue(Object o, String propertyName) {
		Object result = get(o).get(propertyName);
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
			sb.append("Object result = " + PropertyHolder.class.getCanonicalName() + ".getPropertValue(this, \"" + propertyName + "\");");
			sb.append("if (result == null) { return " + defaultValue + "; }");
			sb.append("return ($r) result;");
			return sb.toString();
		} else {
			return "return ($r) " + PropertyHolder.class.getCanonicalName() + ".getPropertValue(this, \"" + propertyName + "\");";
		}
	}

	public static String callSet(String propertyName, String value) {
		return PropertyHolder.class.getCanonicalName() + ".get(this).put(\"" + propertyName + "\", " + value + ");";
	}

}
