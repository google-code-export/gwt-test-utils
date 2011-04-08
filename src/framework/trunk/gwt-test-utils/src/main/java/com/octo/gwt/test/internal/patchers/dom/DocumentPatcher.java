package com.octo.gwt.test.internal.patchers.dom;

import java.util.List;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Document.class)
public class DocumentPatcher extends OverlayPatcher {

  private static int ID = 0;

  @PatchMethod
  public static Text createTextNode(Document document, String data) {
    return JsoFactory.createTextNode(data);
  }

  @PatchMethod
  public static String createUniqueId(Document document) {
    ID++;
    return "elem_" + Long.toString(ID);
  }

  @PatchMethod
  public static Document get() {
    return JsoFactory.getDocument();
  }

  @PatchMethod
  public static BodyElement getBody(Document document) {
    NodeList<Element> bodyList = getElementsByTagName(document, "body");
    if (bodyList.getLength() < 1)
      return null;
    else
      return bodyList.getItem(0).cast();
  }

  @PatchMethod
  public static String getCompatMode(Document document) {
    return "toto";
  }

  @PatchMethod
  public static String getDomain(Document document) {
    return null;
  }

  @PatchMethod
  public static Element getElementById(Node document, String elementId) {
    NodeList<Node> childs = getChildNodeList(document);

    for (int i = 0; i < childs.getLength(); i++) {
      Node n = childs.getItem(i);
      if (Node.ELEMENT_NODE == n.getNodeType()) {
        Element currentElement = n.cast();
        if (elementId.equals(currentElement.getId())) {
          return currentElement;
        }
      }
      Element result = getElementById(n, elementId);
      if (result != null) {
        return result;
      }
    }

    return null;
  }

  @PatchMethod
  public static NodeList<Element> getElementsByTagName(Node node, String tagName) {
    NodeList<Element> result = JsoFactory.createNodeList();

    inspectDomForTag(node, tagName, result);

    return result;
  }

  @PatchMethod
  public static String getReferrer(Document document) {
    return "";
  }

  private static NodeList<Node> getChildNodeList(Node node) {
    return PropertyContainerUtils.getProperty(node,
        DOMProperties.NODE_LIST_FIELD);
  }

  private static void inspectDomForTag(Node node, String tagName,
      NodeList<Element> result) {
    NodeList<Node> childs = getChildNodeList(node);
    List<Node> list = PropertyContainerUtils.getProperty(result,
        DOMProperties.NODE_LIST_INNER_LIST);

    for (int i = 0; i < childs.getLength(); i++) {
      Node n = childs.getItem(i);
      if (Node.ELEMENT_NODE == n.getNodeType()) {
        Element childElem = n.cast();
        if ("*".equals(tagName)
            || tagName.equalsIgnoreCase(childElem.getTagName())) {
          list.add(childElem);
        }
      }
      inspectDomForTag(n, tagName, result);
    }
  }

}
