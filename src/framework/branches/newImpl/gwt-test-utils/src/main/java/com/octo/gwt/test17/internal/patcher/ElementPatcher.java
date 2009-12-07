package com.octo.gwt.test17.internal.patcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.internal.overrides.TagAware;

public class ElementPatcher extends AbstractPatcher {

	public static final String ATTRIBUTS_MAP_FIELD = "attributsMap";
	public static final String PROPERTY_MAP_FIELD = "propertyMap";
	public static final String STYLE_FIELD = "style";

	@Override
	public void initClass(CtClass c) throws Exception {

		CtConstructor cons = c.getConstructors()[0];
		StringBuilder sb = new StringBuilder();
		// init style property
		sb.append("{");
		sb.append(PropertyHolder.callSet(STYLE_FIELD, "new " + Style.class.getCanonicalName() + "()"));
		// init propertyMap
		sb.append(PropertyHolder.callSet(PROPERTY_MAP_FIELD, "new " + HashMap.class.getCanonicalName() + "()"));
		// init attributsMap
		sb.append(PropertyHolder.callSet(ATTRIBUTS_MAP_FIELD, "new " + HashMap.class.getCanonicalName() + "()"));
		sb.append("}");
		cons.setBody(sb.toString());
	}

	@Override
	public boolean patchMethod(CtMethod m) throws Exception {
		if (m.getName().equals("getStyle")) {
			replaceImplementation(m, "getStyle", "this");
		} else if (m.getName().startsWith("getProperty")) {
			replaceImplementation(m, "getProperty", "this, $1");
		} else if (m.getName().startsWith("setProperty")) {
			replaceImplementation(m, "setProperty", "this, $1, ($w) $2");
		} else if ("setAttribute".equals(m.getName())) {
			replaceImplementation(m, "setAttribute", "this, $1, $2");
		} else if (m.getName().equals("getTagName")) {
			replaceImplementation(m, "getTagName", "this");
		} else if ("getElementsByTagName".equals(m.getName())) {
			replaceImplementation(m, "getElementsByTagName", "this, $1");
		} else if ("removeAttribute".equals(m.getName())) {
			replaceImplementation(m, "removeAttribute", "this, $1");
		} else {
			// method has not been patched
			return false;
		}

		return true;
	}

	public static String getTagName(Element element) {
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
		Map<String, String> attrs = (Map<String, String>) PropertyHolder.get(elem).get(ElementPatcher.ATTRIBUTS_MAP_FIELD);
		attrs.remove(name);
	}

	@SuppressWarnings("unchecked")
	public static Object getProperty(Element element, String propertyName) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);

		return propertyMap.get(propertyName);
	}

	@SuppressWarnings("unchecked")
	public static void setProperty(Element element, String propertyName, Object value) {
		Map<String, Object> propertyMap = (Map<String, Object>) PropertyHolder.get(element).get(PROPERTY_MAP_FIELD);
		propertyMap.put(propertyName, value);
	}

	@SuppressWarnings("unchecked")
	public static void setAttribute(Element element, String attributeName, String value) {
		Map<String, String> attributMap = (Map<String, String>) PropertyHolder.get(element).get(ATTRIBUTS_MAP_FIELD);
		attributMap.put(attributeName, value);
	}

}
