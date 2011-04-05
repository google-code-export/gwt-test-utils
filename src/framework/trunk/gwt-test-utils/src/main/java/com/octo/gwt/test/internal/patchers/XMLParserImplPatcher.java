package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.xml.client.impl.XMLParserImpl;
import com.octo.gwt.test.internal.GwtHtmlParser;
import com.octo.gwt.test.internal.patchers.dom.NodeFactory;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(XMLParserImpl.class)
public class XMLParserImplPatcher extends AutomaticPatcher {

  @PatchMethod
  public static JavaScriptObject getElementByIdImpl(
      XMLParserImpl xmlParserImpl, JavaScriptObject jsoDocument, String id) {
    Document document = jsoDocument.cast();
    return document.getElementById(id);
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

    Document document = NodeFactory.createDocument();
    NodeList<Node> nodes = GwtHtmlParser.parse(contents);

    for (int i = 0; i < nodes.getLength(); i++) {
      document.appendChild(nodes.getItem(i));
    }

    return document;
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
