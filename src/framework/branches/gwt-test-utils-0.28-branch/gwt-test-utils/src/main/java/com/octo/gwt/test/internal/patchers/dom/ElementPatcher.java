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
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Element.class)
public class ElementPatcher extends OverlayPatcher {

  @PatchMethod
  public static void blur(Element element) {

  }

  @PatchMethod
  public static void focus(Element element) {

  }

  @PatchMethod
  public static String getClassName(Element element) {
    return element.getAttribute(JsoProperties.ELEM_CLASS);
  }

  @PatchMethod
  public static NodeList<Element> getElementsByTagName(Element elem,
      String tagName) {
    return DocumentPatcher.getElementsByTagName(elem, tagName);
  }

  @PatchMethod
  public static String getId(Element element) {
    return element.getAttribute(JsoProperties.ELEM_ID);
  }

  @PatchMethod
  public static int getOffsetHeight(Element element) {
    return GwtStringUtils.parseInt(element.getStyle().getHeight(), 0);
  }

  @PatchMethod
  public static int getOffsetLeft(Element element) {
    return GwtStringUtils.parseInt(element.getStyle().getLeft(), 0);
  }

  @PatchMethod
  public static Element getOffsetParent(Element element) {
    if (element == null)
      return null;

    return element.getParentElement();
  }

  @PatchMethod
  public static int getOffsetTop(Element element) {
    return GwtStringUtils.parseInt(element.getStyle().getTop(), 0);
  }

  @PatchMethod
  public static int getOffsetWidth(Element element) {
    return GwtStringUtils.parseInt(element.getStyle().getWidth(), 0);
  }

  @PatchMethod
  public static boolean getPropertyBoolean(Element element, String propertyName) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getBoolean(propertyName);
  }

  @PatchMethod
  public static double getPropertyDouble(Element element, String propertyName) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getDouble(propertyName);
  }

  @PatchMethod
  public static int getPropertyInt(Element element, String propertyName) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getInteger(propertyName);
  }

  @PatchMethod
  public static Object getPropertyObject(Element element, String propertyName) {
    // TODO: remove this test ?
    if ("tagName".equals(propertyName)) {
      return element.getTagName();
    }
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getObject(propertyName);
  }

  @PatchMethod
  public static String getPropertyString(Element element, String propertyName) {

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
  public static Style getStyle(Element element) {
    return JavaScriptObjects.getObject(element,
        JsoProperties.STYLE_OBJECT_FIELD);
  }

  @PatchMethod
  public static String getTitle(Element element) {
    return element.getAttribute(JsoProperties.ELEM_TITLE);
  }

  @PatchMethod
  public static void removeAttribute(Element element, String name) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.remove(name);
  }

  @PatchMethod
  public static void setAttribute(Element element, String attributeName,
      String value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(attributeName.toLowerCase(), value);
  }

  @PatchMethod
  public static void setClassName(Element element, String className) {
    element.setAttribute(JsoProperties.ELEM_CLASS, className);
  }

  @PatchMethod
  public static void setId(Element element, String id) {
    element.setAttribute(JsoProperties.ELEM_ID, id);
  }

  @PatchMethod
  public static void setInnerHTML(Element element, String html) {
    List<Node> innerList = JavaScriptObjects.getObject(element.getChildNodes(),
        JsoProperties.NODE_LIST_INNER_LIST);
    innerList.clear();

    NodeList<Node> nodes = GwtHtmlParser.parse(html);

    for (int i = 0; i < nodes.getLength(); i++) {
      innerList.add(nodes.getItem(i));
    }
  }

  @PatchMethod
  public static void setPropertyBoolean(Element element, String propertyName,
      boolean value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(propertyName.toLowerCase(), value);
  }

  @PatchMethod
  public static void setPropertyDouble(Element element, String propertyName,
      double value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(propertyName.toLowerCase(), value);
  }

  @PatchMethod
  public static void setPropertyInt(Element element, String propertyName,
      int value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(propertyName.toLowerCase(), value);
  }

  @PatchMethod
  public static void setPropertyString(Element element, String propertyName,
      String value) {
    PropertyContainer properties = JavaScriptObjects.getObject(element,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(propertyName.toLowerCase(), value);
  }

  @PatchMethod
  public static void setTitle(Element element, String title) {
    element.setAttribute(JsoProperties.ELEM_TITLE, title);
  }

}
