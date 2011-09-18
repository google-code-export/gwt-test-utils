package com.octo.gwt.test.internal.patchers.dom;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.utils.GwtHtmlParser;
import com.octo.gwt.test.internal.utils.GwtStringUtils;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Element.class)
class ElementPatcher {

  @PatchMethod
  static void blur(Element element) {

  }

  @PatchMethod
  static void focus(Element element) {

  }

  @PatchMethod
  static int getClientHeight(Element element) {
    return JavaScriptObjects.getInteger(element,
        JsoProperties.ELEMENT_CLIENT_HEIGHT);
  }

  @PatchMethod
  static int getClientWidth(Element element) {
    return JavaScriptObjects.getInteger(element,
        JsoProperties.ELEMENT_CLIENT_WIDTH);
  }

  @PatchMethod
  static NodeList<Element> getElementsByTagName(Element elem, String tagName) {
    return DocumentPatcher.getElementsByTagName(elem, tagName);
  }

  @PatchMethod
  static int getOffsetHeight(Element element) {
    return GwtStringUtils.parseInt(element.getStyle().getHeight(), 0);
  }

  @PatchMethod
  static int getOffsetLeft(Element element) {
    return GwtStringUtils.parseInt(element.getStyle().getLeft(), 0);
  }

  @PatchMethod
  static Element getOffsetParent(Element element) {
    if (element == null)
      return null;

    return element.getParentElement();
  }

  @PatchMethod
  static int getOffsetTop(Element element) {
    return GwtStringUtils.parseInt(element.getStyle().getTop(), 0);
  }

  @PatchMethod
  static int getOffsetWidth(Element element) {
    return GwtStringUtils.parseInt(element.getStyle().getWidth(), 0);
  }

  @PatchMethod
  static boolean getPropertyBoolean(Element element, String propertyName) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getBoolean(propertyName);
  }

  @PatchMethod
  static double getPropertyDouble(Element element, String propertyName) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getDouble(propertyName);
  }

  @PatchMethod
  static int getPropertyInt(Element element, String propertyName) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getInteger(propertyName);
  }

  @PatchMethod
  static JavaScriptObject getPropertyJSO(Element element, String propertyName) {
    return (JavaScriptObject) getPropertyObject(element, propertyName);
  }

  @PatchMethod
  static Object getPropertyObject(Element element, String propertyName) {
    if ("tagName".equals(propertyName)) {
      return element.getTagName().toUpperCase();
    } else if ("style".equals(propertyName)) {
      return element.getStyle();
    }

    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);

    return properties.getObject(propertyName);
  }

  @PatchMethod
  static String getPropertyString(Element element, String propertyName) {

    Object value = getPropertyObject(element, propertyName);

    // null (javascript undefined) is a possible value here if not a DOM
    // standard property
    if (value == null
        && JsoProperties.get().isStandardDOMProperty(propertyName)) {
      return "";
    } else if (value == null) {
      return null;
    } else {
      return value.toString();
    }

  }

  @PatchMethod
  static Style getStyle(Element element) {
    return JavaScriptObjects.getObject(element,
        JsoProperties.STYLE_OBJECT_FIELD);
  }

  @PatchMethod
  static void removeAttribute(Element element, String name) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);

    String standardDOMPropertyName = JsoProperties.get().getStandardDOMPropertyName(
        name);

    if (standardDOMPropertyName != null) {
      properties.remove(standardDOMPropertyName);
    } else {
      properties.remove(name.toLowerCase());
    }
  }

  @PatchMethod
  static void setAttribute(Element element, String attributeName, String value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);

    String standardDOMPropertyName = JsoProperties.get().getStandardDOMPropertyName(
        attributeName);

    if (standardDOMPropertyName != null) {
      properties.put(standardDOMPropertyName, value);
    } else {
      properties.put(attributeName.toLowerCase(), value);
    }
  }

  @PatchMethod
  static void setInnerHTML(Element element, String html) {
    List<Node> innerList = JavaScriptObjects.getObject(element.getChildNodes(),
        JsoProperties.NODE_LIST_INNER_LIST);
    innerList.clear();

    NodeList<Node> nodes = GwtHtmlParser.parse(html, true);

    for (int i = 0; i < nodes.getLength(); i++) {
      innerList.add(nodes.getItem(i));
    }
  }

  @PatchMethod
  static void setPropertyBoolean(Element element, String name, boolean value) {
    setPropertyObject(element, name, value);
  }

  @PatchMethod
  static void setPropertyDouble(Element element, String name, double value) {
    setPropertyObject(element, name, value);
  }

  @PatchMethod
  static void setPropertyInt(Element element, String name, int value) {
    setPropertyObject(element, name, value);
  }

  @PatchMethod
  static void setPropertyJSO(Element element, String name,
      JavaScriptObject value) {
    setPropertyObject(element, name, value);
  }

  @PatchMethod
  static void setPropertyObject(Element element, String name, Object value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);

    properties.put(name, value);
  }

  @PatchMethod
  static void setPropertyString(Element element, String name, String value) {
    setPropertyObject(element, name, value);
  }

}
