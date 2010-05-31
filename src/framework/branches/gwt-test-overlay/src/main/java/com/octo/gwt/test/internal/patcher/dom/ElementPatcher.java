package com.octo.gwt.test.internal.patcher.dom;

import java.util.ArrayList;
import java.util.List;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.patcher.tools.AutomaticGetAndSetPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.PropertyContainer;
import com.octo.gwt.test.internal.patcher.tools.PropertyContainerAwareHelper;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.TagAware;

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
		
		cons.insertAfter(PropertyContainerAwareHelper.getCodeSetProperty("this", STYLE_FIELD, JavaScriptObjectFactory.class.getCanonicalName() + ".createJavaScriptObject(" + Style.class.getCanonicalName() + ".class)") + ";");
		cons.insertAfter(PropertyContainerAwareHelper.getCodeSetProperty("this", PROPERTY_MAP_FIELD, "new " + PropertyContainer.class.getCanonicalName() + "()") + ";");
		cons.insertAfter(PropertyContainerAwareHelper.getCodeSetProperty("this", ATTRIBUTE_MAP_FIELD, "new " + PropertyContainer.class.getCanonicalName() + "()") + ";");
		cons.insertAfter(PropertyContainerAwareHelper.getCodeSetProperty("this", ATTRIBUTE_MAP_FIELD, "new " + PropertyContainer.class.getCanonicalName() + "()") + ";");
		cons.insertAfter(PropertyContainerAwareHelper.getCodeSetProperty("this", CLASSNAME_FIELD, "\"\"") + ";");
		cons.insertAfter(PropertyContainerAwareHelper.getCodeSetProperty("this", ACCESSKEY_FIELD, "\"\"") + ";");
	}

	@PatchMethod
	public static String getTagName(Element element) {
		if (element == null)
			return null;

		if (element instanceof TagAware) {
			return ((TagAware) element).getTag();
		}
		return GwtTestReflectionUtils.getStaticFieldValue(element.getClass(), "TAG");
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
		return PropertyContainerAwareHelper.getProperty(element, STYLE_FIELD);
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
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.remove(name);
	}

	@PatchMethod
	public static boolean getPropertyBoolean(Element element, String propertyName) {
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return propertyContainer.getBoolean(propertyName);
	}

	@PatchMethod
	public static double getPropertyDouble(Element element, String propertyName) {
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return propertyContainer.getDouble(propertyName);
	}

	@PatchMethod
	public static int getPropertyInt(Element element, String propertyName) {
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return propertyContainer.getInteger(propertyName);
	}

	@PatchMethod
	public static String getPropertyString(Element element, String propertyName) {
		if ("tagName".equals(propertyName)) {
			return getTagName(element);
		}
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		return (String) propertyContainer.get(propertyName);
	}

	@PatchMethod
	public static void setPropertyString(Element element, String propertyName, String value) {
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}
	
	@PatchMethod
	public static void setPropertyInt(Element element, String propertyName, int value) {
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}

	@PatchMethod
	public static void setPropertyBoolean(Element element, String propertyName, boolean value) {
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}
	
	@PatchMethod
	public static void setPropertyDouble(Element element, String propertyName, double value) {
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(propertyName, value);
	}

	@PatchMethod
	public static void setAttribute(Element element, String attributeName, String value) {
		PropertyContainer propertyContainer = PropertyContainerAwareHelper.getProperty(element, PROPERTY_MAP_FIELD);
		propertyContainer.put(attributeName, value);
	}

}
