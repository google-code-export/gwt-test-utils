package com.octo.gwt.test17.internal.patcher.dom;

import java.util.ArrayList;
import java.util.List;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.internal.overrides.TagAware;
import com.octo.gwt.test17.ng.AutomaticGetAndSetPatcher;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.PropertyContainer;
import com.octo.gwt.test17.ng.SubClassedHelper;

public class ElementPatcher extends AutomaticGetAndSetPatcher {

	public static final String PROPERTY_MAP_FIELD = "propertyMap";
	public static final String ATTRIBUTE_MAP_FIELD = "attributeMap";
	public static final String STYLE_FIELD = "style";
	public static final String CLASSNAME_FIELD = "ClassName";
	public static final String ACCESSKEY_FIELD = "AccessKey";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);
		
		cons.insertAfter(SubClassedHelper.getCodeSetProperty("this", STYLE_FIELD, NodeFactory.class.getCanonicalName() + ".createStyle()", true) + ";");
		cons.insertAfter(SubClassedHelper.getCodeSetProperty("this", PROPERTY_MAP_FIELD, "new " + PropertyContainer.class.getCanonicalName() + "()", true) + ";");
		cons.insertAfter(SubClassedHelper.getCodeSetProperty("this", ATTRIBUTE_MAP_FIELD, "new " + PropertyContainer.class.getCanonicalName() + "()", true) + ";");
		cons.insertAfter(SubClassedHelper.getCodeSetProperty("this", ATTRIBUTE_MAP_FIELD, "new " + PropertyContainer.class.getCanonicalName() + "()", true) + ";");
		cons.insertAfter(SubClassedHelper.getCodeSetProperty("this", CLASSNAME_FIELD, "\"\"", true) + ";");
		cons.insertAfter(SubClassedHelper.getCodeSetProperty("this", ACCESSKEY_FIELD, "\"\"", true) + ";");
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
		return SubClassedHelper.getProperty(element, STYLE_FIELD);
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
	public static void removeAttribute(Element element, String name) {
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.remove(name);
	}

	@PatchMethod
	public static boolean getPropertyBoolean(Element element, String propertyName) {
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return propertyContainer.getBoolean(propertyName);
	}

	@PatchMethod
	public static double getPropertyDouble(Element element, String propertyName) {
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return propertyContainer.getDouble(propertyName);
	}

	@PatchMethod
	public static int getPropertyInt(Element element, String propertyName) {
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return propertyContainer.getInteger(propertyName);
	}

	@PatchMethod
	public static String getPropertyString(Element element, String propertyName) {
		if ("tagName".equals(propertyName)) {
			return getTagName(element);
		}
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return (String) propertyContainer.get(propertyName);
	}

	@PatchMethod
	public static void setPropertyString(Element element, String propertyName, String value) {
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}
	
	@PatchMethod
	public static void setPropertyInt(Element element, String propertyName, int value) {
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}

	@PatchMethod
	public static void setPropertyBoolean(Element element, String propertyName, boolean value) {
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}
	
	@PatchMethod
	public static void setPropertyDouble(Element element, String propertyName, double value) {
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}

	@PatchMethod
	public static void setAttribute(Element element, String attributeName, String value) {
		PropertyContainer propertyContainer = SubClassedHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(attributeName, value);
	}

}
