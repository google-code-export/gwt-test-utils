package com.octo.gwt.test17.internal.patcher.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.internal.overrides.TagAware;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class ElementPatcher extends AutomaticPatcher {

	public static final String PROPERTY_MAP_FIELD = "propertyMap";
	public static final String STYLE_FIELD = "style";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);
		StringBuilder sb = new StringBuilder();
		// init style property
		sb.append("{");
		sb.append(PropertyHolder.callSet(STYLE_FIELD, "new " + Style.class.getCanonicalName() + "()"));
		// init propertyMap
		sb.append(PropertyHolder.callSet(PROPERTY_MAP_FIELD, "new " + HashMap.class.getCanonicalName() + "()"));
		sb.append("}");
		cons.setBody(sb.toString());
	}

	@PatchMethod
	public static String getTagName(Element element) {
		if (element == null)
			return null;

		if (element instanceof TagAware) {
			return ((TagAware) element).getTag();
		}
		return ReflectionUtils.getStaticFieldValue(element.getClass(), "TAG");
	}

	@PatchMethod
	public static Style getStyle(Element element) {
		return (Style) PropertyHolder.get(element).get(STYLE_FIELD);
	}

	@PatchMethod
	public static NodeList<Element> getElementsByTagName(Element elem, String name) {
		NodeList<Node> nodeList = elem.getChildNodes();

		List<Element> list = new ArrayList<Element>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.getItem(i);

			if (node instanceof Element) {
				Element child = (Element) node;
				if (name.equals("*")) {
					list.add(child);
				} else if (name.equals(child.getTagName())) {
					list.add(child);
				}

			}
		}

		return new OverrideNodeList<Element>(list);
	}

	@PatchMethod
	@SuppressWarnings("unchecked")
	public static void removeAttribute(Element elem, String name) {
		Map<String, String> attrs = (Map<String, String>) PropertyHolder.get(elem).get(PROPERTY_MAP_FIELD);
		attrs.remove(name);
	}

	@PatchMethod
	@SuppressWarnings("unchecked")
	public static boolean getPropertyBoolean(Element element, String propertyName) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);

		Boolean b = (Boolean) propertyMap.get(propertyName);

		if (b == null) {
			return false;
		}

		return b;
	}

	@PatchMethod
	@SuppressWarnings("unchecked")
	public static double getPropertyDouble(Element element, String propertyName) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);

		Double d = (Double) propertyMap.get(propertyName);

		if (d == null) {
			return 0;
		}

		return d;
	}

	@PatchMethod
	@SuppressWarnings("unchecked")
	public static int getPropertyInt(Element element, String propertyName) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);

		Integer i = (Integer) propertyMap.get(propertyName);

		if (i == null) {
			return 0;
		}

		return i;
	}

	@PatchMethod
	@SuppressWarnings("unchecked")
	public static String getPropertyString(Element element, String propertyName) {
		if ("tagName".equals(propertyName)) {
			return getTagName(element);
		}
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);

		return (String) propertyMap.get(propertyName);
	}

	@PatchMethod
	@SuppressWarnings("unchecked")
	public static void setPropertyString(Element element, String propertyName, String value) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);
		propertyMap.put(propertyName, value);
	}
	
	@SuppressWarnings("unchecked")
	@PatchMethod
	public static void setPropertyInt(Element element, String propertyName, int value) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);
		propertyMap.put(propertyName, value);
	}
	
	@SuppressWarnings("unchecked")
	@PatchMethod
	public static void setPropertyBoolean(Element element, String propertyName, boolean value) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);
		propertyMap.put(propertyName, value);
	}
	
	@SuppressWarnings("unchecked")
	@PatchMethod
	public static void setPropertyDouble(Element element, String propertyName, double value) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);
		propertyMap.put(propertyName, value);
	}

	@PatchMethod
	@SuppressWarnings("unchecked")
	public static void setAttribute(Element element, String attributeName, String value) {
		Map<String, String> attributMap = (Map<String, String>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);
		attributMap.put(attributeName, value);
	}

}
