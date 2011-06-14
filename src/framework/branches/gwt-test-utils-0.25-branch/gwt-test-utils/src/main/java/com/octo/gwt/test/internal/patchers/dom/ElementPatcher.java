package com.octo.gwt.test.internal.patchers.dom;

import java.util.List;

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
  static String getClassName(Element element) {
    return element.getAttribute(JsoProperties.ELEM_CLASS);
  }

  @PatchMethod
  static NodeList<Element> getElementsByTagName(Element elem, String tagName) {
    return DocumentPatcher.getElementsByTagName(elem, tagName);
  }

  @PatchMethod
  static String getId(Element element) {
    return element.getAttribute(JsoProperties.ELEM_ID);
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
  static Object getPropertyObject(Element element, String propertyName) {
    // TODO: remove this test ?
    if ("tagName".equals(propertyName)) {
      return element.getTagName();
    }
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getObject(propertyName);
  }

  @PatchMethod
  static String getPropertyString(Element element, String propertyName) {

    String value = (String) getPropertyObject(element, propertyName);

    // null (javascript undefined) is a possible value here if not a DOM
    // standard property
    if (value == null) {
      String propertyNameCaseSensitive = JsoProperties.get().getPropertyName(
          propertyName);
      if (propertyName.equals(propertyNameCaseSensitive)) {
        value = "";
      }
    }

    return value;
  }

  @PatchMethod
  static Style getStyle(Element element) {
    return JavaScriptObjects.getObject(element,
        JsoProperties.STYLE_OBJECT_FIELD);
  }

  @PatchMethod
  static String getTitle(Element element) {
    return element.getAttribute(JsoProperties.ELEM_TITLE);
  }

  @PatchMethod
  static void removeAttribute(Element element, String name) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.remove(name);
  }

  @PatchMethod
  static void setAttribute(Element element, String attributeName, String value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(attributeName.toLowerCase(), value);
  }

  @PatchMethod
  static void setClassName(Element element, String className) {
    element.setAttribute(JsoProperties.ELEM_CLASS, className);
  }

  @PatchMethod
  static void setId(Element element, String id) {
    element.setAttribute(JsoProperties.ELEM_ID, id);
  }

  @PatchMethod
  static void setInnerHTML(Element element, String html) {
    List<Node> innerList = JavaScriptObjects.getObject(element.getChildNodes(),
        JsoProperties.NODE_LIST_INNER_LIST);
    innerList.clear();

    NodeList<Node> nodes = GwtHtmlParser.parse(html);

    for (int i = 0; i < nodes.getLength(); i++) {
      innerList.add(nodes.getItem(i));
    }
  }

  @PatchMethod
  static void setPropertyBoolean(Element element, String propertyName,
      boolean value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(propertyName.toLowerCase(), value);
  }

  @PatchMethod
  static void setPropertyDouble(Element element, String propertyName,
      double value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(propertyName.toLowerCase(), value);
  }

  @PatchMethod
  static void setPropertyInt(Element element, String propertyName, int value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(propertyName.toLowerCase(), value);
  }

  @PatchMethod
  static void setPropertyString(Element element, String propertyName,
      String value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(propertyName.toLowerCase(), value);
  }

  @PatchMethod
  static void setTitle(Element element, String title) {
    element.setAttribute(JsoProperties.ELEM_TITLE, title);
  }

}
