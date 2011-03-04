package com.octo.gwt.test.internal.patchers.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.GwtHtmlParser;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.utils.GwtTestStringUtils;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patchers.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Element.class)
public class ElementPatcher extends AutomaticPropertyContainerPatcher {

	public static final String PROPERTY_MAP_FIELD = "propertyMap";
	public static final String STYLE_FIELD = "Style";
	public static final String CLASSNAME_FIELD = "ClassName";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);

		cons.insertAfter(PropertyContainerHelper.getCodeSetProperty("this", STYLE_FIELD, NodeFactory.class.getCanonicalName() + ".createStyle(this)")
				+ ";");
		cons.insertAfter(PropertyContainerHelper.getCodeSetProperty("this", PROPERTY_MAP_FIELD, PropertyContainerHelper.getConstructionCode()) + ";");
		cons.insertAfter(PropertyContainerHelper.getCodeSetProperty("this", CLASSNAME_FIELD, "\"\"") + ";");
	}

	@PatchMethod
	public static Element getOffsetParent(Element element) {
		if (element == null)
			return null;

		return element.getParentElement();
	}

	@PatchMethod
	public static void blur(Element element) {

	}

	@PatchMethod
	public static void focus(Element element) {

	}

	@PatchMethod
	public static Style getStyle(Element element) {
		return PropertyContainerHelper.getProperty(element, STYLE_FIELD);
	}

	@PatchMethod
	public static NodeList<Element> getElementsByTagName(Element elem, String tagName) {
		return DocumentPatcher.getElementsByTagName(elem, tagName);
	}

	@PatchMethod
	public static void removeAttribute(Element element, String name) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.remove(name);
	}

	@PatchMethod
	public static boolean getPropertyBoolean(Element element, String propertyName) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return propertyContainer.getBoolean(propertyName);
	}

	@PatchMethod
	public static double getPropertyDouble(Element element, String propertyName) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return propertyContainer.getDouble(propertyName);
	}

	@PatchMethod
	public static int getPropertyInt(Element element, String propertyName) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return propertyContainer.getInteger(propertyName);
	}

	@PatchMethod
	public static String getPropertyString(Element element, String propertyName) {
		if ("tagName".equals(propertyName)) {
			return element.getTagName();
		}
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);

		// null is a possible value here
		return (String) propertyContainer.get(propertyName);
	}

	@PatchMethod
	public static void setPropertyString(Element element, String propertyName, String value) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}

	@PatchMethod
	public static void setPropertyInt(Element element, String propertyName, int value) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}

	@PatchMethod
	public static void setPropertyBoolean(Element element, String propertyName, boolean value) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}

	@PatchMethod
	public static void setPropertyDouble(Element element, String propertyName, double value) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}

	@PatchMethod
	public static void setAttribute(Element element, String attributeName, String value) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(attributeName, value);
	}

	@PatchMethod
	public static void setInnerHTML(Element element, String html) {
		OverrideNodeList<Node> list = (OverrideNodeList<Node>) element.getChildNodes();
		list.getList().clear();

		NodeList<Node> nodes = GwtHtmlParser.parse(html);

		for (int i = 0; i < nodes.getLength(); i++) {
			list.getList().add(nodes.getItem(i));
		}

		PropertyContainerHelper.setProperty(element, "InnerHTML", html);
	}

	@PatchMethod
	public static void setId(Element element, String id) {
		element.setAttribute("id", id);
	}

	@PatchMethod
	public static String getId(Element element) {
		return element.getAttribute("id");
	}

	@PatchMethod
	public static void setClassName(Element element, String className) {
		element.setAttribute("class", className);
	}

	@PatchMethod
	public static String getClassName(Element element) {
		return element.getAttribute("class");
	}

	@PatchMethod
	public static int getOffsetHeight(Element element) {
		return GwtTestStringUtils.parseInt(element.getStyle().getHeight(), 0);
	}

	@PatchMethod
	public static int getOffsetLeft(Element element) {
		return GwtTestStringUtils.parseInt(element.getStyle().getLeft(), 0);
	}

	@PatchMethod
	public static int getOffsetTop(Element element) {
		return GwtTestStringUtils.parseInt(element.getStyle().getTop(), 0);
	}

	@PatchMethod
	public static int getOffsetWidth(Element element) {
		return GwtTestStringUtils.parseInt(element.getStyle().getWidth(), 0);
	}

}
