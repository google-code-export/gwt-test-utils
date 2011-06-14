package com.octo.gwt.test.internal.patchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;
import com.google.gwt.xml.client.impl.XMLParserImpl;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.GwtXMLParser;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(XMLParserImpl.class)
public class XMLParserImplPatcher {

  @PatchMethod
  public static JavaScriptObject appendChild(JavaScriptObject jsObject,
      JavaScriptObject newChildJs) {
    Node n = jsObject.cast();
    Node newChildNode = newChildJs.cast();
    return n.appendChild(newChildNode);
  }

  @PatchMethod
  public static JavaScriptObject createCDATASection(JavaScriptObject jsObject,
      String data) {
    Text text = JavaScriptObjects.newObject(Text.class);

    // set the special type
    JavaScriptObjects.setProperty(text, JsoProperties.NODE_TYPE_FIELD,
        com.google.gwt.xml.client.Node.CDATA_SECTION_NODE);

    text.setData(data);

    return text;
  }

  @PatchMethod
  public static JavaScriptObject createDocumentImpl(XMLParserImpl xmlParserImpl) {
    return JavaScriptObjects.newObject(Document.class);
  }

  @PatchMethod
  public static JavaScriptObject createElement(JavaScriptObject jsObject,
      String tagName) {
    Document document = jsObject.cast();
    return document.createElement(tagName);
  }

  @PatchMethod
  public static String getAttribute(JavaScriptObject o, String name) {
    // Attribute return by XML node can be null
    PropertyContainer properties = JavaScriptObjects.getObject(o,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getObject(name);
  }

  @PatchMethod
  public static JavaScriptObject getAttributeNode(JavaScriptObject o,
      String name) {
    String value = getAttribute(o, name);

    // create the JavaScriptObject which will simulate an google.xml.Attr
    Node attrJSO = JavaScriptObjects.newObject(Node.class);
    JavaScriptObjects.setProperty(attrJSO, JsoProperties.NODE_TYPE_FIELD,
        com.google.gwt.xml.client.Node.ATTRIBUTE_NODE);
    JavaScriptObjects.setProperty(attrJSO, JsoProperties.XML_ATTR_NAME, name);
    JavaScriptObjects.setProperty(attrJSO, JsoProperties.XML_ATTR_VALUE, value);
    JavaScriptObjects.setProperty(attrJSO, JsoProperties.NODE_NAMESPACE_URI,
        getNamespaceURI(o));

    return attrJSO;
  }

  @PatchMethod
  public static JavaScriptObject getAttributes(JavaScriptObject t) {
    Set<String> attrSet = JavaScriptObjects.getObject(t,
        JsoProperties.XML_ATTR_SET);

    List<Node> list = new ArrayList<Node>();

    for (String attrName : attrSet) {
      Node attrNode = getAttributeNode(t, attrName).cast();
      list.add(attrNode);
    }

    return JavaScriptObjects.newNodeList(list);
  }

  @PatchMethod
  public static JavaScriptObject getChildNodes(JavaScriptObject t) {
    Node n = t.cast();
    return n.getChildNodes();
  }

  @PatchMethod
  public static String getData(JavaScriptObject o) {
    Text text = o.cast();
    return text.getData();
  }

  @PatchMethod
  public static JavaScriptObject getDocumentElement(JavaScriptObject o) {
    Document document = o.cast();
    return document.getFirstChild();
  }

  @PatchMethod
  public static JavaScriptObject getElementByIdImpl(
      XMLParserImpl xmlParserImpl, JavaScriptObject jsoDocument, String id) {
    Document document = jsoDocument.cast();
    return document.getElementById(id);
  }

  @PatchMethod
  public static JavaScriptObject getElementsByTagNameImpl(
      XMLParserImpl xmlParserImpl, JavaScriptObject o, String tagName) {
    Node node = o.cast();
    NodeList<Element> nodeList;

    switch (node.getNodeType()) {
      case Node.DOCUMENT_NODE:
        Document document = node.cast();
        nodeList = document.getElementsByTagName(tagName);
        break;
      case Node.ELEMENT_NODE:
        Element element = node.cast();
        nodeList = element.getElementsByTagName(tagName);
        break;
      default:
        nodeList = JavaScriptObjects.newNodeList();
        break;
    }

    return nodeList;
  }

  @PatchMethod
  public static int getLength(JavaScriptObject o) {
    NodeList<Node> nodeList = o.cast();
    return nodeList.getLength();
  }

  @PatchMethod
  public static String getName(JavaScriptObject o) {
    return JavaScriptObjects.getString(o, JsoProperties.XML_ATTR_NAME);
  }

  @PatchMethod
  public static JavaScriptObject getNamedItem(JavaScriptObject t, String name) {
    NodeList<Node> attrs = t.cast();

    for (int i = 0; i < attrs.getLength(); i++) {
      Node n = attrs.getItem(i);
      if (name.equals(getName(n))) {
        return n;
      }
    }

    return null;
  }

  @PatchMethod
  public static String getNamespaceURI(JavaScriptObject o) {
    return JavaScriptObjects.getString(o, JsoProperties.NODE_NAMESPACE_URI);
  }

  @PatchMethod
  public static JavaScriptObject getNextSibling(JavaScriptObject o) {
    Node n = o.cast();
    return n.getNextSibling();
  }

  @PatchMethod
  public static String getNodeName(JavaScriptObject o) {
    try {
      Node node = o.cast();
      return node.getNodeName();
    } catch (ClassCastException e) {
      // TODO: remove this when cast() will be fine
      return "";
    }
  }

  @PatchMethod
  public static short getNodeType(JavaScriptObject jsObject) {
    Node node = jsObject.cast();
    return node.getNodeType();
  }

  @PatchMethod
  public static String getNodeValue(JavaScriptObject o) {
    Node n = o.cast();
    switch (n.getNodeType()) {
      case com.google.gwt.xml.client.Node.ATTRIBUTE_NODE:
        return JavaScriptObjects.getString(n, JsoProperties.XML_ATTR_VALUE);
      case Node.ELEMENT_NODE:
        Element e = n.cast();
        return e.getInnerText();
      default:
        return n.getNodeValue();
    }
  }

  @PatchMethod
  public static JavaScriptObject getPreviousSibling(JavaScriptObject o) {
    Node n = o.cast();
    return n.getPreviousSibling();
  }

  @PatchMethod
  public static String getTagName(JavaScriptObject o) {
    Element e = o.cast();
    return e.getTagName();
  }

  @PatchMethod
  public static String getValue(JavaScriptObject o) {
    return JavaScriptObjects.getString(o, JsoProperties.XML_ATTR_VALUE);
  }

  @PatchMethod
  public static boolean hasChildNodes(JavaScriptObject jsObject) {
    Node n = jsObject.cast();
    return n.hasChildNodes();
  }

  @PatchMethod
  public static JavaScriptObject item(JavaScriptObject t, int index) {
    NodeList<Node> nodeList = t.cast();
    return nodeList.getItem(index);
  }

  @PatchMethod
  public static JavaScriptObject parseImpl(XMLParserImpl xmlParserImpl,
      String contents) {

    try {
      return GwtXMLParser.parse(contents);
    } catch (Exception e) {
      throw new GwtTestPatchException("Error while parsing XML", e);
    }
  }

  @PatchMethod
  public static JavaScriptObject removeChild(JavaScriptObject jsObject,
      JavaScriptObject oldChildJs) {
    Node node = jsObject.cast();
    Node oldChildNode = oldChildJs.cast();

    return node.removeChild(oldChildNode);
  }

  @PatchMethod
  public static void setNodeValue(JavaScriptObject jsObject, String nodeValue) {
    Node n = jsObject.cast();
    switch (n.getNodeType()) {
      case Node.TEXT_NODE:
        Text text = n.cast();
        text.setData(nodeValue);
        break;
      case Node.ELEMENT_NODE:
        Element element = n.cast();
        element.setInnerText(nodeValue);
        break;
      case Node.DOCUMENT_NODE:
        // nothing to do
        break;
    }
  }

}
