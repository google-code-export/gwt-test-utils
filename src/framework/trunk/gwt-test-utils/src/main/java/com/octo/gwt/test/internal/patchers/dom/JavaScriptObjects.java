package com.octo.gwt.test.internal.patchers.dom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javassist.CtClass;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.AreaElement;
import com.google.gwt.dom.client.BRElement;
import com.google.gwt.dom.client.BaseElement;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DListElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FieldSetElement;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.dom.client.FrameSetElement;
import com.google.gwt.dom.client.HRElement;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.dom.client.LegendElement;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.MapElement;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.ModElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OListElement;
import com.google.gwt.dom.client.ObjectElement;
import com.google.gwt.dom.client.OptGroupElement;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.ParamElement;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.dom.client.QuoteElement;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.TableCaptionElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.dom.client.Text;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.dom.client.TitleElement;
import com.google.gwt.dom.client.UListElement;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class JavaScriptObjects {

  public static final String PROPERTIES = "JSO_PROPERTIES";

  public static CtClass STRING_TYPE;

  private static final Map<String, Class<? extends Element>> elementMap = new TreeMap<String, Class<? extends Element>>();

  static {
    elementMap.put("a", AnchorElement.class);
    elementMap.put("area", AreaElement.class);
    elementMap.put("base", BaseElement.class);
    elementMap.put("body", BodyElement.class);
    elementMap.put("br", BRElement.class);
    elementMap.put("button", ButtonElement.class);
    elementMap.put("div", DivElement.class);
    elementMap.put("dl", DListElement.class);
    elementMap.put("fieldset", FieldSetElement.class);
    elementMap.put("form", FormElement.class);
    elementMap.put("frame", FrameElement.class);
    elementMap.put("frameset", FrameSetElement.class);
    elementMap.put("head", HeadElement.class);
    elementMap.put("hr", HRElement.class);
    elementMap.put("h1", HeadingElement.class);
    elementMap.put("h2", HeadingElement.class);
    elementMap.put("h3", HeadingElement.class);
    elementMap.put("h4", HeadingElement.class);
    elementMap.put("h5", HeadingElement.class);
    elementMap.put("h6", HeadingElement.class);
    elementMap.put("hr", HRElement.class);
    elementMap.put("iframe", IFrameElement.class);
    elementMap.put("img", ImageElement.class);
    elementMap.put("ins", ModElement.class);
    elementMap.put("del", ModElement.class);
    elementMap.put("input", InputElement.class);
    elementMap.put("label", LabelElement.class);
    elementMap.put("legend", LegendElement.class);
    elementMap.put("li", LIElement.class);
    elementMap.put("link", LinkElement.class);
    elementMap.put("map", MapElement.class);
    elementMap.put("meta", MetaElement.class);
    elementMap.put("object", ObjectElement.class);
    elementMap.put("ol", OListElement.class);
    elementMap.put("optgroup", OptGroupElement.class);
    elementMap.put("option", OptionElement.class);
    elementMap.put("options", OptionElement.class);
    elementMap.put("p", ParagraphElement.class);
    elementMap.put("param", ParamElement.class);
    elementMap.put("pre", PreElement.class);
    elementMap.put("q", QuoteElement.class);
    elementMap.put("blockquote", QuoteElement.class);
    elementMap.put("script", ScriptElement.class);
    elementMap.put("select", SelectElement.class);
    elementMap.put("span", SpanElement.class);
    elementMap.put("style", StyleElement.class);
    elementMap.put("caption", TableCaptionElement.class);
    elementMap.put("td", TableCellElement.class);
    elementMap.put("th", TableCellElement.class);
    elementMap.put("col", TableColElement.class);
    elementMap.put("colgroup", TableColElement.class);
    elementMap.put("table", TableElement.class);
    elementMap.put("tr", TableRowElement.class);
    elementMap.put("tbody", TableSectionElement.class);
    elementMap.put("tfoot", TableSectionElement.class);
    elementMap.put("thead", TableSectionElement.class);
    elementMap.put("textarea", TextAreaElement.class);
    elementMap.put("title", TitleElement.class);
    elementMap.put("ul", UListElement.class);
  }

  public static void clearProperties(JavaScriptObject jso) {
    getJsoProperties(jso).clear();
  }

  public static Set<Map.Entry<String, Object>> entrySet(JavaScriptObject jso) {
    return getJsoProperties(jso).entrySet();
  }

  public static boolean getBoolean(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso).getBoolean(propName);
  }

  public static double getDouble(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso).getDouble(propName);
  }

  public static int getInteger(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso).getInteger(propName);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getObject(JavaScriptObject jso, String propName) {
    return (T) getJsoProperties(jso).getObject(propName);
  }

  public static short getShort(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso).getShort(propName);
  }

  public static String getString(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso).getString(propName);
  }

  public static Element newElement(String tag) {
    Class<? extends Element> clazz = elementMap.get(tag.toLowerCase());

    if (clazz == null) {
      clazz = Element.class;
    }

    Element elem = newObject(clazz);

    setProperty(elem, JsoProperties.TAG_NAME, tag);

    if (tag.equalsIgnoreCase("html")) {
      setProperty(elem, JsoProperties.NODE_NAME, "HTML");
    }

    return elem;
  }

  public static <T extends Node> NodeList<T> newNodeList() {
    return newNodeList(new ArrayList<T>());
  }

  @SuppressWarnings("unchecked")
  public static <T extends Node> NodeList<T> newNodeList(List<T> innerList) {
    NodeList<T> nodeList = newObject(NodeList.class);

    setProperty(nodeList, JsoProperties.NODE_LIST_INNER_LIST, innerList);

    return nodeList;
  }

  public static <T extends JavaScriptObject> T newObject(Class<T> jsoClass) {
    // TODO : need to work with => JavaScriptObject.createObject().cast()
    T o = GwtReflectionUtils.instantiateClass(jsoClass);

    short nodeType = -1;

    if (Node.class.isAssignableFrom(jsoClass)) {
      setProperty(o, JsoProperties.NODE_LIST_FIELD, newNodeList());
    }

    if (Document.class.isAssignableFrom(jsoClass)) {
      nodeType = Node.DOCUMENT_NODE;
    } else if (Element.class.isAssignableFrom(jsoClass)) {
      nodeType = Node.ELEMENT_NODE;

      Element e = o.cast();
      setProperty(e, JsoProperties.STYLE_OBJECT_FIELD, newStyle(e));

      // a propertyContainer with a LinkedHashMap to record the order of DOM
      // properties
      PropertyContainer elemProperties = PropertyContainer.newInstance(new LinkedHashMap<String, Object>());
      setProperty(e, JsoProperties.ELEM_PROPERTIES, elemProperties);

      if (SelectElement.class.isAssignableFrom(jsoClass)) {
        setProperty(o, JsoProperties.SELECTED_INDEX_FIELD, -1);
        setProperty(o, JsoProperties.SELECTED_SIZE, -1);
      }
    } else if (Text.class.isAssignableFrom(jsoClass)) {
      nodeType = Node.TEXT_NODE;
    }

    setProperty(o, JsoProperties.NODE_TYPE_FIELD, nodeType);

    return o;
  }

  public static Style newStyle(Element owner) {
    Style style = newObject(Style.class);

    setProperty(style, JsoProperties.STYLE_TARGET_ELEMENT, owner);
    setProperty(style, JsoProperties.STYLE_WHITESPACE_PROPERTY, "nowrap");

    return style;
  }

  public static Text newText(String data) {
    Text text = newObject(Text.class);
    text.setData(data);

    return text;
  }

  public static void remove(JavaScriptObject jso, String propName) {
    getJsoProperties(jso).remove(propName);
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      boolean value) {
    getJsoProperties(jso).put(propName, Boolean.valueOf(value));
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      double value) {
    getJsoProperties(jso).put(propName, Double.valueOf(value));
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      int value) {
    getJsoProperties(jso).put(propName, Integer.valueOf(value));
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      long value) {
    getJsoProperties(jso).put(propName, Long.valueOf(value));
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      Object value) {
    getJsoProperties(jso).put(propName, value);
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      short value) {
    getJsoProperties(jso).put(propName, Short.valueOf(value));
  }

  private static PropertyContainer getJsoProperties(JavaScriptObject o) {
    return GwtReflectionUtils.getPrivateFieldValue(o, PROPERTIES);
  }

  private JavaScriptObjects() {
  }
}
