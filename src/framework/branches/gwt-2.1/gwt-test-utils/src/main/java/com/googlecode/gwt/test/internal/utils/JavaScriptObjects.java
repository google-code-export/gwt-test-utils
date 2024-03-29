package com.googlecode.gwt.test.internal.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.UIObject;
import com.googlecode.gwt.test.finder.GwtFinder;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

/**
 * Utilities for Overlay types support in gwt-test-utils. <strong>For internal
 * use only.<strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class JavaScriptObjects {

  public static final String ID = "id";

  /**
   * The name of the internal {@link PropertyContainer} which is add in
   * {@link JavaScriptObject} class during class rewrite process
   */
  public static final String PROPERTIES = "properties";

  private static final Set<String> DOM_PROPERTIES = new HashSet<String>() {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    {
      /*
       * 
       * Parse HTML standard attributes here :
       * http://www.w3.org/TR/html4/index/attributes.html
       * 
       * With this jQuery script :
       * 
       * <script language="Javascript"> var array = new Array();
       * $('td[title=Name]').each(function() { var text = $(this).text().trim();
       * if (jQuery.inArray(text, array) == -1) { array.push(text); } });
       * 
       * var java = '<p>'; var length = array.length;
       * 
       * for (var i=0; i < length; i++) { java += 'add("' + array[i] +
       * '");<br/>'; }
       * 
       * java += '</p>';
       * 
       * $('table').parent().html(java); </script>
       */
      add("abbr");
      add("accept-charset");
      add("accept");
      add("accesskey");
      add("action");
      add("align");
      add("alink");
      add("alt");
      add("archive");
      add("axis");
      add("background");
      add("bgcolor");
      add("border");
      add("cellpadding");
      add("cellspacing");
      add("char");
      add("charoff");
      add("charset");
      add("checked");
      add("cite");
      add("class");
      add("classid");
      add("clear");
      add("code");
      add("codebase");
      add("codetype");
      add("color");
      add("cols");
      add("colspan");
      add("compact");
      add("content");
      add("coords");
      add("data");
      add("datetime");
      add("declare");
      add("defer");
      add("dir");
      add("disabled");
      add("enctype");
      add("face");
      add("for");
      add("frame");
      add("frameborder");
      add("headers");
      add("height");
      add("href");
      add("hreflang");
      add("hspace");
      add("http-equiv");
      add("id");
      add("ismap");
      add("label");
      add("lang");
      add("language");
      add("link");
      add("longdesc");
      add("marginheight");
      add("marginwidth");
      add("maxlength");
      add("media");
      add("method");
      add("multiple");
      add("name");
      add("nohref");
      add("noresize");
      add("noshade");
      add("nowrap");
      add("object");
      add("onblur");
      add("onchange");
      add("onclick");
      add("ondblclick");
      add("onfocus");
      add("onkeydown");
      add("onkeypress");
      add("onkeyup");
      add("onload");
      add("onmousedown");
      add("onmousemove");
      add("onmouseout");
      add("onmouseover");
      add("onmouseup");
      add("onreset");
      add("onselect");
      add("onsubmit");
      add("onunload");
      add("profile");
      add("prompt");
      add("readonly");
      add("rel");
      add("rev");
      add("rows");
      add("rowspan");
      add("rules");
      add("scheme");
      add("scope");
      add("scrolling");
      add("selected");
      add("shape");
      add("size");
      add("span");
      add("src");
      add("standby");
      add("start");
      add("style");
      add("summary");
      add("tabindex");
      add("target");
      add("text");
      add("title");
      add("type");
      add("usemap");
      add("valign");
      add("value");
      add("valuetype");
      add("version");
      add("vlink");
      add("vspace");
      add("width");
    }
  };

  private static final String ELEM_PROPERTIES = "ELEM_PROPERTIES";
  private static final String NODE_LIST_FIELD = "childNodes";
  private static final String NODE_LIST_INNER_LIST = "NODE_LIST_INNER_LIST";
  private static final String NODE_NAME = "nodeName";
  private static final String NODE_TYPE_FIELD = "nodeType";
  private static final String PARENT_NODE_FIELD = "parentNode";
  private static final String STYLE_OBJECT_FIELD = "STYLE_OBJECT";
  private static final String STYLE_PROPERTIES = "STYLE_PROPERTIES";
  private static final String TAG_NAME = "tagName";

  public static void clearProperties(JavaScriptObject jso) {
    getJsoProperties(jso).clear();
  }

  public static JavaScriptObject cloneJso(JavaScriptObject oldJso, boolean deep) {
    JavaScriptObject newJso = JavaScriptObject.createObject();

    if (Node.is(oldJso)) {
      short nodeType = oldJso.<Node> cast().getNodeType();
      setProperty(newJso, NODE_TYPE_FIELD, nodeType);
    }

    for (Map.Entry<String, Object> entry : JavaScriptObjects.entrySet(oldJso)) {

      if (PARENT_NODE_FIELD.equals(entry.getKey())) {
        // Nothing to do : new cloned node does not have any parent
      } else if (NODE_TYPE_FIELD.equals(entry.getKey())) {
        // ignore it since it has to be handled at the very beginning of cloning
      } else if (JsoProperties.NODE_OWNER_DOCUMENT.equals(entry.getKey())) {
        JavaScriptObjects.setProperty(newJso,
            JsoProperties.NODE_OWNER_DOCUMENT, JavaScriptObjects.getObject(
                oldJso, JsoProperties.NODE_OWNER_DOCUMENT));
      } else if (STYLE_OBJECT_FIELD.equals(entry.getKey())) {
        cloneStyle(newJso, (Style) entry.getValue());
      } else if (NODE_LIST_FIELD.equals(entry.getKey())) {
        Node newNode = newJso.cast();
        Node oldNode = oldJso.cast();
        cloneChildNodes(newNode, oldNode, deep);
      } else if (ELEM_PROPERTIES.equals(entry.getKey())) {
        PropertyContainer newPc = getDomProperties(newJso.<Element> cast());
        PropertyContainer oldPc = getDomProperties(oldJso.<Element> cast());

        for (Map.Entry<String, Object> entry2 : oldPc.entrySet()) {
          newPc.put(entry2.getKey(), entry2.getValue());
        }
      } else if (JavaScriptObject.class.isInstance(entry.getValue())) {
        JavaScriptObject oldChildJso = (JavaScriptObject) entry.getValue();
        JavaScriptObject newChildJso = cloneJso(oldChildJso, deep);
        JavaScriptObjects.setProperty(newJso, entry.getKey(), newChildJso);
      } else {
        // copy the property, which should be a String or a primitive type (or
        // corresponding wrapper object)
        JavaScriptObjects.setProperty(newJso, entry.getKey(), entry.getValue());
      }
    }

    return newJso;

  }

  public static Set<Map.Entry<String, Object>> entrySet(JavaScriptObject jso) {
    return getJsoProperties(jso).entrySet();
  }

  public static boolean getBoolean(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso, propName).getBoolean(propName);
  }

  @SuppressWarnings("unchecked")
  public static List<Node> getChildNodeInnerList(Node node) {
    NodeList<Node> nodeList = getChildNodes(node);
    return (List<Node>) JavaScriptObjects.getObject(nodeList,
        NODE_LIST_INNER_LIST);
  }

  @SuppressWarnings("unchecked")
  public static <T extends Node> List<T> getChildNodeInnerList(
      NodeList<T> nodeList) {
    assert isNodeList(nodeList) : "not a NodeList";

    return (List<T>) JavaScriptObjects.getObject(nodeList, NODE_LIST_INNER_LIST);
  }

  public static NodeList<Node> getChildNodes(Node node) {
    assert Node.is(node) : "not a Node";
    NodeList<Node> nodeList = JavaScriptObjects.getObject(node, NODE_LIST_FIELD);

    if (nodeList == null) {
      nodeList = newNodeList();
      JavaScriptObjects.setProperty(node, NODE_LIST_FIELD, nodeList);
    }

    return nodeList;
  }

  public static PropertyContainer getDomProperties(Element element) {
    assert Node.is(element) : "not a Node";

    PropertyContainer pc = getObject(element, ELEM_PROPERTIES);

    if (pc == null) {
      // a propertyContainer with a LinkedHashMap to record the order of DOM
      // properties
      pc = PropertyContainer.newInstance(new LinkedHashMap<String, Object>());
      setProperty(element, ELEM_PROPERTIES, pc);
    }

    return pc;
  }

  public static double getDouble(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso, propName).getDouble(propName);
  }

  public static int getInteger(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso, propName).getInteger(propName);
  }

  public static short getNodeType(JavaScriptObject jso) {
    return hasProperty(jso, NODE_TYPE_FIELD) ? getShort(jso, NODE_TYPE_FIELD)
        : -1;
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

  public static Style getStyle(Element element) {
    assert Element.is(element) : "not an Element";

    Style style = getObject(element, STYLE_OBJECT_FIELD);
    if (style == null) {
      style = newStyle();
      setProperty(element, STYLE_OBJECT_FIELD, style);
    }

    return style;

  }

  public static LinkedHashMap<String, String> getStyleProperties(Style style) {
    LinkedHashMap<String, String> properties = getObject(style,
        STYLE_PROPERTIES);

    assert properties != null : "not a Style";

    return properties;
  }

  public static String getTagName(Element element) {
    assert Element.is(element) : "not an Element";

    return getString(element, TAG_NAME);
  }

  public static boolean hasProperty(JavaScriptObject jso, String propName) {
    return getJsoProperties(jso).contains(propName);
  }

  public static boolean isNodeList(JavaScriptObject jso) {
    return hasProperty(jso, NODE_LIST_INNER_LIST);
  }

  public static boolean isStandardDOMProperty(String propertyName) {
    return "className".equals(propertyName) || !"class".equals(propertyName)
        && DOM_PROPERTIES.contains(propertyName);
  }

  public static boolean isStyle(JavaScriptObject jso) {
    return hasProperty(jso, STYLE_PROPERTIES);
  }

  public static Document newDocument() {
    Document document = newNode(Node.DOCUMENT_NODE).cast();
    setProperty(document, JsoProperties.NODE_OWNER_DOCUMENT, document);
    return document;

  }

  public static Element newElement(String tag, Document ownerDocument) {
    Element elem = newNode(Node.ELEMENT_NODE).cast();
    setProperty(elem, TAG_NAME, tag);
    setProperty(elem, JsoProperties.NODE_OWNER_DOCUMENT, ownerDocument);

    if (tag.equalsIgnoreCase("html")) {
      setProperty(elem, NODE_NAME, "HTML");
    }

    return elem;
  }

  public static Node newNode(int nodeType) {
    Node newNode = JavaScriptObject.createObject().cast();
    setProperty(newNode, NODE_TYPE_FIELD, (short) nodeType);
    return newNode;
  }

  public static <T extends Node> NodeList<T> newNodeList() {
    return newNodeList(new ArrayList<T>());
  }

  @SuppressWarnings("unchecked")
  public static <T extends Node> NodeList<T> newNodeList(List<T> innerList) {
    NodeList<T> nodeList = (NodeList<T>) JavaScriptObject.createObject().cast();
    setProperty(nodeList, NODE_LIST_INNER_LIST, innerList);
    return nodeList;
  }

  public static Style newStyle() {
    Style style = JavaScriptObject.createObject().cast();

    setProperty(style, STYLE_PROPERTIES, new LinkedHashMap<String, String>());

    return style;
  }

  public static Text newText(String data, Document ownerDocument) {
    Text text = newNode(Node.TEXT_NODE).cast();
    setProperty(text, JsoProperties.NODE_OWNER_DOCUMENT, ownerDocument);
    text.setData(data);

    return text;
  }

  public static void onSetId(JavaScriptObject jso, String newId, String oldId) {
    EventListener listener = DOM.getEventListener(jso.<com.google.gwt.user.client.Element> cast());

    if (UIObject.class.isInstance(listener)) {
      GwtReflectionUtils.callStaticMethod(GwtFinder.class, "onSetId", listener,
          newId, oldId);
    }
  }

  public static void remove(JavaScriptObject jso, String propName) {
    getJsoProperties(jso, propName).remove(propName);
  }

  public static void setParentNode(Node child, Node parent) {
    JavaScriptObjects.setProperty(child, PARENT_NODE_FIELD, parent);
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

    if (ID.equals(propName)) {
      onSetId(jso, value.toString(), getString(jso, ID));
    }
    getJsoProperties(jso, propName).put(propName, value);
  }

  public static void setProperty(JavaScriptObject jso, String propName,
      short value) {
    getJsoProperties(jso, propName).put(propName, Short.valueOf(value));
  }

  private static void cloneChildNodes(Node newNode, Node oldNode, boolean deep) {

    List<Node> childs = JavaScriptObjects.getChildNodeInnerList(oldNode);
    if (deep) {
      // copy all child nodes
      for (Node child : childs) {
        newNode.appendChild(child.cloneNode(deep));
      }
    } else {
      // only copy the TextNode if exists
      for (Node child : childs) {
        if (Node.TEXT_NODE == child.getNodeType()) {
          newNode.appendChild(Document.get().createTextNode(
              child.getNodeValue()));
          break;
        }
      }
    }
  }

  private static void cloneStyle(JavaScriptObject newNode, Style oldStyle) {
    Style newStyle = getStyle(newNode.<Element> cast());
    Map<String, String> oldProperties = getStyleProperties(oldStyle);
    Map<String, String> newProperties = getStyleProperties(newStyle);
    newProperties.putAll(oldProperties);
  }

  private static PropertyContainer getJsoProperties(JavaScriptObject o) {
    PropertyContainer pc = GwtReflectionUtils.getPrivateFieldValue(o,
        PROPERTIES);

    if (pc == null) {
      pc = PropertyContainer.newInstance(new HashMap<String, Object>());
      GwtReflectionUtils.setPrivateFieldValue(o, PROPERTIES, pc);
    }

    return pc;
  }

  private static PropertyContainer getJsoProperties(JavaScriptObject o,
      String propertyName) {
    if (isStandardDOMProperty(propertyName)) {
      // case for standard dom properties, like "id", "name", "title"...
      return getDomProperties(o.<Element> cast());
    } else {
      return getJsoProperties(o);
    }

  }

  private JavaScriptObjects() {
  }
}
