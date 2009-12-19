package com.octo.gwt.test17.internal.patcher.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.CtConstructor;
import javassist.CtMethod;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.internal.overrides.TagAware;
import com.octo.gwt.test17.internal.patcher.AbstractPatcher;

public class ElementPatcher extends AbstractPatcher {

	public static final String PROPERTY_MAP_FIELD = "propertyMap";
	public static final String STYLE_FIELD = "style";

	@Override
	public void initClass() throws Exception {
		CtConstructor cons = findConstructor();
		StringBuilder sb = new StringBuilder();
		// init style property
		sb.append("{");
		sb.append(PropertyHolder.callSet(STYLE_FIELD, "new " + Style.class.getCanonicalName() + "()"));
		// init propertyMap
		sb.append(PropertyHolder.callSet(PROPERTY_MAP_FIELD, "new " + HashMap.class.getCanonicalName() + "()"));
		sb.append("}");
		cons.setBody(sb.toString());
	}

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getStyle")) {
			return callMethod("getStyle", "this");
		} else if (match(m, "getPropertyBoolean")) {
			return callMethod("getPropertyBoolean", "this, $1");
		} else if (match(m, "getPropertyDouble")) {
			return callMethod("getPropertyDouble", "this, $1");
		} else if (match(m, "getPropertyInt")) {
			return callMethod("getPropertyInt", "this, $1");
		} else if (match(m, "getPropertyString")) {
			return callMethod("getPropertyString", "this, $1");
		} else if (match(m, "setProperty.*")) {
			return callMethod("setProperty", "this, $1, ($w) $2");
		} else if (match(m, "setAttribute")) {
			return callMethod("setAttribute", "this, $1, $2");
		} else if (match(m, "getTagName")) {
			return callMethod("getTagName", "this");
		} else if (match(m, "getElementsByTagName")) {
			return callMethod("getElementsByTagName", "this, $1");
		} else if (match(m, "removeAttribute")) {
			return callMethod("removeAttribute", "this, $1");
		}

		return null;
	}

	public static String getTagName(Element element) {
		if (element == null)
			return null;

		if (element instanceof TagAware) {
			return ((TagAware) element).getTag();
		}
		return ReflectionUtils.getStaticFieldValue(element.getClass(), "TAG");
	}

	public static Style getStyle(Element element) {
		return (Style) PropertyHolder.get(element).get(STYLE_FIELD);
	}

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

	@SuppressWarnings("unchecked")
	public static void removeAttribute(Element elem, String name) {
		Map<String, String> attrs = (Map<String, String>) PropertyHolder.get(elem).get(PROPERTY_MAP_FIELD);
		attrs.remove(name);
	}

	@SuppressWarnings("unchecked")
	public static boolean getPropertyBoolean(Element element, String propertyName) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);

		Boolean b = (Boolean) propertyMap.get(propertyName);

		if (b == null) {
			return false;
		}

		return b;
	}

	@SuppressWarnings("unchecked")
	public static double getPropertyDouble(Element element, String propertyName) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);

		Double d = (Double) propertyMap.get(propertyName);

		if (d == null) {
			return 0;
		}

		return d;
	}

	@SuppressWarnings("unchecked")
	public static int getPropertyInt(Element element, String propertyName) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);

		Integer i = (Integer) propertyMap.get(propertyName);

		if (i == null) {
			return 0;
		}

		return i;
	}

	@SuppressWarnings("unchecked")
	public static String getPropertyString(Element element, String propertyName) {
		if ("tagName".equals(propertyName)) {
			return getTagName(element);
		}
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);

		return (String) propertyMap.get(propertyName);
	}

	@SuppressWarnings("unchecked")
	public static void setProperty(Element element, String propertyName, Object value) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);
		propertyMap.put(propertyName, value);
	}

	@SuppressWarnings("unchecked")
	public static void setAttribute(Element element, String attributeName, String value) {
		Map<String, String> attributMap = (Map<String, String>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);
		attributMap.put(attributeName, value);
	}

}
