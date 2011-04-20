package com.octo.gwt.test.internal.patchers.dom;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.GwtHtmlParser;
import com.octo.gwt.test.internal.utils.GwtStringUtils;
import com.octo.gwt.test.internal.utils.JsoProperties;
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
    return element.getAttribute("class");
  }

  @PatchMethod
  public static NodeList<Element> getElementsByTagName(Element elem,
      String tagName) {
    return DocumentPatcher.getElementsByTagName(elem, tagName);
  }

  @PatchMethod
  public static String getId(Element element) {
    return element.getAttribute("id");
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
    return JavaScriptObjects.getJsoProperties(element).getBoolean(propertyName);
  }

  @PatchMethod
  public static double getPropertyDouble(Element element, String propertyName) {
    return JavaScriptObjects.getJsoProperties(element).getDouble(propertyName);
  }

  @PatchMethod
  public static int getPropertyInt(Element element, String propertyName) {
    return JavaScriptObjects.getJsoProperties(element).getInteger(propertyName);
  }

  @PatchMethod
  public static Object getPropertyObject(Element element, String propertyName) {
    if ("tagName".equals(propertyName)) {
      return element.getTagName();
    }
    return JavaScriptObjects.getJsoProperties(element).getObject(propertyName);
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
    return JavaScriptObjects.getJsoProperties(element).getObject(
        JsoProperties.STYLE_OBJECT_FIELD);
  }

  @PatchMethod
  public static void removeAttribute(Element element, String name) {
    JavaScriptObjects.getJsoProperties(element).remove(name);
  }

  @PatchMethod
  public static void setAttribute(Element element, String attributeName,
      String value) {
    JavaScriptObjects.getJsoProperties(element).put(attributeName, value);
  }

  @PatchMethod
  public static void setClassName(Element element, String className) {
    element.setAttribute("class", className);
  }

  @PatchMethod
  public static void setId(Element element, String id) {
    element.setAttribute("id", id);
  }

  @PatchMethod
  public static void setInnerHTML(Element element, String html) {
    List<Node> innerList = JavaScriptObjects.getJsoProperties(
        element.getChildNodes()).getObject(JsoProperties.NODE_LIST_INNER_LIST);
    innerList.clear();

    NodeList<Node> nodes = GwtHtmlParser.parse(html);

    for (int i = 0; i < nodes.getLength(); i++) {
      innerList.add(nodes.getItem(i));
    }

    JavaScriptObjects.getJsoProperties(element).put(JsoProperties.INNER_HTML,
        html);
  }

  @PatchMethod
  public static void setPropertyBoolean(Element element, String propertyName,
      boolean value) {
    JavaScriptObjects.getJsoProperties(element).put(propertyName, value);
  }

  @PatchMethod
  public static void setPropertyDouble(Element element, String propertyName,
      double value) {
    JavaScriptObjects.getJsoProperties(element).put(propertyName, value);
  }

  @PatchMethod
  public static void setPropertyInt(Element element, String propertyName,
      int value) {
    JavaScriptObjects.getJsoProperties(element).put(propertyName, value);
  }

  @PatchMethod
  public static void setPropertyString(Element element, String propertyName,
      String value) {
    JavaScriptObjects.getJsoProperties(element).put(propertyName, value);
  }

}
