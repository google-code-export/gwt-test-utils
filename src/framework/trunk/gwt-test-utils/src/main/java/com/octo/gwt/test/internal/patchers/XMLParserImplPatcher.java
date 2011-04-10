package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.xml.client.impl.XMLParserImpl;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.xml.GwtXMLParser;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(XMLParserImpl.class)
public class XMLParserImplPatcher extends AutomaticPatcher {

  @PatchMethod
  public static String getAttribute(JavaScriptObject o, String name) {
    Element element = o.cast();
    return element.getAttribute(name);
  }

  @PatchMethod
  public static JavaScriptObject getAttributeNode(JavaScriptObject o,
      String name) {
    String value = getAttribute(o, name);

    // create the JavaScriptObject which will simulate an google.xml.Attr
    Node attrJSO = JavaScriptObjects.newObject(Node.class);
    JavaScriptObjects.getJsoProperties(attrJSO).put(
        JsoProperties.NODE_TYPE_FIELD,
        com.google.gwt.xml.client.Node.ATTRIBUTE_NODE);
    JavaScriptObjects.getJsoProperties(attrJSO).put(
        JsoProperties.XML_ATTR_NAME, name);
    JavaScriptObjects.getJsoProperties(attrJSO).put(
        JsoProperties.XML_ATTR_VALUE, value);

    return attrJSO;
  }

  @PatchMethod
  public static JavaScriptObject getElementByIdImpl(
      XMLParserImpl xmlParserImpl, JavaScriptObject jsoDocument, String id) {
    Document document = jsoDocument.cast();
    return document.getElementById(id);
  }

  @PatchMethod
  public static String getNamespaceURI(JavaScriptObject jsObject) {
    return JavaScriptObjects.getJsoProperties(jsObject).getString(
        JsoProperties.NODE_NAMESPACE_URI);
  }

  @PatchMethod
  public static short getNodeType(JavaScriptObject jsObject) {
    Node node = jsObject.cast();
    return node.getNodeType();
  }

  @PatchMethod
  public static String getTagName(JavaScriptObject o) {
    Element e = o.cast();
    return e.getTagName();
  }

  @PatchMethod
  public static JavaScriptObject parseImpl(XMLParserImpl xmlParserImpl,
      String contents) {

    try {
      return GwtXMLParser.parse(contents);
    } catch (Exception e) {
      throw new GwtTestPatchException("Error while parsing XML", e);
    }
    // if (nodes.getLength() > 0) {
    // Element e0 = nodes.getItem(0).cast();
    // if ("?".equals(e0.getTagName())) {
    // document.appendChild(nodes.getItem(1);
    // } else {
    // return nodes.getItem(0);
    // }
    // } else {
    // return null;
    // }
  }
}
