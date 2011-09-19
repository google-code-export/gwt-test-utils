package com.octo.gwt.test.internal.patchers.dom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javassist.CtClass;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
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

  private static final Map<String, Class<? extends Element>> ELEMENT_TAGS = new TreeMap<String, Class<? extends Element>>();

  static {
    ELEMENT_TAGS.put("a", AnchorElement.class);
    ELEMENT_TAGS.put("area", AreaElement.class);
    ELEMENT_TAGS.put("base", BaseElement.class);
    ELEMENT_TAGS.put("body", BodyElement.class);
    ELEMENT_TAGS.put("br", BRElement.class);
    ELEMENT_TAGS.put("button", ButtonElement.class);
    ELEMENT_TAGS.put("div", DivElement.class);
    ELEMENT_TAGS.put("dl", DListElement.class);
    ELEMENT_TAGS.put("fieldset", FieldSetElement.class);
    ELEMENT_TAGS.put("form", FormElement.class);
    ELEMENT_TAGS.put("frame", FrameElement.class);
    ELEMENT_TAGS.put("frameset", FrameSetElement.class);
    ELEMENT_TAGS.put("head", HeadElement.class);
    ELEMENT_TAGS.put("hr", HRElement.class);
    ELEMENT_TAGS.put("h1", HeadingElement.class);
    ELEMENT_TAGS.put("h2", HeadingElement.class);
    ELEMENT_TAGS.put("h3", HeadingElement.class);
    ELEMENT_TAGS.put("h4", HeadingElement.class);
    ELEMENT_TAGS.put("h5", HeadingElement.class);
    ELEMENT_TAGS.put("h6", HeadingElement.class);
    ELEMENT_TAGS.put("hr", HRElement.class);
    ELEMENT_TAGS.put("iframe", IFrameElement.class);
    ELEMENT_TAGS.put("img", ImageElement.class);
    ELEMENT_TAGS.put("ins", ModElement.class);
    ELEMENT_TAGS.put("del", ModElement.class);
    ELEMENT_TAGS.put("input", InputElement.class);
    ELEMENT_TAGS.put("label", LabelElement.class);
    ELEMENT_TAGS.put("legend", LegendElement.class);
    ELEMENT_TAGS.put("li", LIElement.class);
    ELEMENT_TAGS.put("link", LinkElement.class);
    ELEMENT_TAGS.put("map", MapElement.class);
    ELEMENT_TAGS.put("meta", MetaElement.class);
    ELEMENT_TAGS.put("object", ObjectElement.class);
    ELEMENT_TAGS.put("ol", OListElement.class);
    ELEMENT_TAGS.put("optgroup", OptGroupElement.class);
    ELEMENT_TAGS.put("option", OptionElement.class);
    ELEMENT_TAGS.put("options", OptionElement.class);
    ELEMENT_TAGS.put("p", ParagraphElement.class);
    ELEMENT_TAGS.put("param", ParamElement.class);
    ELEMENT_TAGS.put("pre", PreElement.class);
    ELEMENT_TAGS.put("q", QuoteElement.class);
    ELEMENT_TAGS.put("blockquote", QuoteElement.class);
    ELEMENT_TAGS.put("script", ScriptElement.class);
    ELEMENT_TAGS.put("select", SelectElement.class);
    ELEMENT_TAGS.put("span", SpanElement.class);
    ELEMENT_TAGS.put("style", StyleElement.class);
    ELEMENT_TAGS.put("caption", TableCaptionElement.class);
    ELEMENT_TAGS.put("td", TableCellElement.class);
    ELEMENT_TAGS.put("th", TableCellElement.class);
    ELEMENT_TAGS.put("col", TableColElement.class);
    ELEMENT_TAGS.put("colgroup", TableColElement.class);
    ELEMENT_TAGS.put("table", TableElement.class);
    ELEMENT_TAGS.put("tr", TableRowElement.class);
    ELEMENT_TAGS.put("tbody", TableSectionElement.class);
    ELEMENT_TAGS.put("tfoot", TableSectionElement.class);
    ELEMENT_TAGS.put("thead", TableSectionElement.class);
    ELEMENT_TAGS.put("textarea", TextAreaElement.class);
    ELEMENT_TAGS.put("title", TitleElement.class);
    ELEMENT_TAGS.put("ul", UListElement.class);
  }

  public static void clearProperties(JavaScriptObject jso) {
    getJsoProperties(jso).clear();
  }

  public static Set<Map.Entry<String, Object>> entrySet(JavaScriptObject jso) {
    return getJsoProperties(jso).entrySet();
  }

  public static boolean getBoolean(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso, propName).getBoolean(propName);
  }

  public static double getDouble(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso, propName).getDouble(propName);
  }

  public static int getInteger(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso, propName).getInteger(propName);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getObject(JavaScriptObject jso, String propName) {
    return (T) getJsoProperties(jso, propName).getObject(propName);
  }

  public static short getShort(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso, propName).getShort(propName);
  }

  public static String getString(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso, propName).getString(propName);
  }

  public static Element newElement(String tag) {
    Class<? extends Element> clazz = ELEMENT_TAGS.get(tag.toLowerCase());

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
      setProperty(o, JsoProperties.STYLE_OBJECT_FIELD, newStyle(e));

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
    } else if (JsArrayString.class.isAssignableFrom(jsoClass)) {
      setProperty(o, JsoProperties.JSARRAY_WRAPPED_LIST,
          new ArrayList<String>());
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
    getJsoProperties(jso, propName).remove(propName);
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      boolean value) {
    getJsoProperties(jso, propName).put(propName, Boolean.valueOf(value));
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      double value) {
    getJsoProperties(jso, propName).put(propName, Double.valueOf(value));
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      int value) {
    getJsoProperties(jso, propName).put(propName, Integer.valueOf(value));
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      long value) {
    getJsoProperties(jso, propName).put(propName, Long.valueOf(value));
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      Object value) {
    getJsoProperties(jso, propName).put(propName, value);
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      short value) {
    getJsoProperties(jso, propName).put(propName, Short.valueOf(value));
  }

  private static PropertyContainer getJsoProperties(JavaScriptObject o) {
    return GwtReflectionUtils.getPrivateFieldValue(o, PROPERTIES);
  }

  private static PropertyContainer getJsoProperties(JavaScriptObject o,
      String propertyName) {
    if (o instanceof Element
        && JsoProperties.get().isStandardDOMProperty(propertyName)) {
      // case for standard dom properties, like "id", "name", "title"...
      return getJsoProperties(o).getObject(JsoProperties.ELEM_PROPERTIES);
    } else {
      return getJsoProperties(o);
    }

  }

  private JavaScriptObjects() {
  }
}
