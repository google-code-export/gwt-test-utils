package com.octo.gwt.test.internal.patchers.dom;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.exceptions.GwtTestDomException;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Node.class)
public class NodePatcher extends OverlayPatcher {

  @PatchMethod
  public static Node appendChild(Node parent, Node newChild) {
    return insertAtIndex(parent, newChild, -1);
  }

  @PatchMethod
  public static Node cloneNode(Node node, boolean deep) {
    Node newNode;
    switch (node.getNodeType()) {
      case Node.ELEMENT_NODE:
        Element e = node.cast();
        newNode = JavaScriptObjects.newElement(e.getTagName());
        break;
      case Node.DOCUMENT_NODE:
        newNode = JavaScriptObjects.newObject(Document.class);
        break;
      case Node.TEXT_NODE:
        newNode = JavaScriptObjects.newObject(Text.class);
        break;
      default:
        throw new GwtTestDomException("Cannot create a Node of type ["
            + node.getClass().getName() + "]");
    }

    copyJSOProperties(newNode, node, deep);

    return newNode;
  }

  @PatchMethod
  public static Node getFirstChild(Node node) {
    List<Node> list = getChildNodeList(node);

    if (list.size() == 0) {
      return null;
    }

    return list.get(0);
  }

  @PatchMethod
  public static Node getLastChild(Node node) {
    List<Node> list = getChildNodeList(node);

    if (list.size() == 0) {
      return null;
    }

    return list.get(list.size() - 1);
  }

  @PatchMethod
  public static Node getNextSibling(Node node) {
    Node parent = node.getParentNode();
    if (parent == null)
      return null;

    List<Node> list = getChildNodeList(parent);

    for (int i = 0; i < list.size(); i++) {
      Node current = list.get(i);
      if (current.equals(node) && i < list.size() - 1) {
        return list.get(i + 1);
      }
    }

    return null;
  }

  @PatchMethod
  public static String getNodeName(Node node) {
    switch (node.getNodeType()) {
      case Node.DOCUMENT_NODE:
        return "#document";
      case Node.ELEMENT_NODE:
        Element e = node.cast();
        return e.getTagName();
      case Node.TEXT_NODE:
        return "#text";
      case com.google.gwt.xml.client.Node.ATTRIBUTE_NODE:
        return JavaScriptObjects.getJsoProperties(node).getString(
            JsoProperties.XML_ATTR_NAME);
      default:
        throw new GwtTestDomException(
            "Invalid Node type (not a Document / Element / Text / Attribute) : "
                + node.getNodeType());
    }
  }

  @PatchMethod
  public static short getNodeType(Node node) {
    short nodeType = JavaScriptObjects.getJsoProperties(node).getShort(
        JsoProperties.NODE_TYPE_FIELD);

    return nodeType != 0 ? nodeType : -1;
  }

  @PatchMethod
  public static String getNodeValue(Node node) {
    switch (node.getNodeType()) {
      case Node.DOCUMENT_NODE:
        return null;
      case Node.ELEMENT_NODE:
        return null;
      case Node.TEXT_NODE:
        Text text = node.cast();
        return text.getData();
      default:
        throw new GwtTestDomException(
            "Invalid Node type (not a Document / Element / Text : "
                + node.getNodeType());
    }
  }

  @PatchMethod
  public static Document getOwnerDocument(Node node) {
    return Document.get();
  }

  @PatchMethod
  public static Node getPreviousSibling(Node node) {
    Node parent = node.getParentNode();
    if (parent == null)
      return null;

    List<Node> list = getChildNodeList(parent);

    for (int i = 0; i < list.size(); i++) {
      Node current = list.get(i);
      if (current.equals(node) && i > 0) {
        return list.get(i - 1);
      }
    }

    return null;
  }

  @PatchMethod
  public static boolean hasChildNodes(Node node) {
    return getChildNodeList(node).size() > 0;
  }

  public static Node insertAtIndex(Node parent, Node newChild, int index) {
    List<Node> list = getChildNodeList(parent);

    // First, remove from old parent
    Node oldParent = newChild.getParentNode();
    if (oldParent != null) {
      oldParent.removeChild(newChild);
    }

    // Then, check parent doesn't contain newChild and remove it if necessary
    list.remove(newChild);

    // Finally, add
    if (index == -1 || index >= list.size()) {
      list.add(newChild);
    } else {
      list.add(index, newChild);
    }

    // Manage getParentNode()
    JavaScriptObjects.getJsoProperties(newChild).put(
        JsoProperties.PARENT_NODE_FIELD, parent);

    return newChild;
  }

  @PatchMethod
  public static Node insertBefore(Node parent, Node newChild, Node refChild) {
    List<Node> list = getChildNodeList(parent);

    // get the index of refChild
    int index = -1;
    if (refChild != null) {
      int i = 0;
      while (index == -1 && i < list.size()) {
        if (list.get(i).equals(refChild)) {
          index = i;
        }
        i++;
      }
    }

    // Then insert by index
    return insertAtIndex(parent, newChild, index);
  }

  @PatchMethod
  public static boolean is(JavaScriptObject object) {
    if (object == null || !(object instanceof Node)) {
      return false;
    }

    return true;
  }

  @PatchMethod
  public static Node removeChild(Node oldParent, Node oldChild) {
    List<Node> list = getChildNodeList(oldParent);

    if (list.remove(oldChild)) {
      return oldChild;
    } else {
      return null;
    }
  }

  @PatchMethod
  public static Node replaceChild(Node parent, Node newChild, Node oldChild) {
    if (oldChild != null) {
      List<Node> list = getChildNodeList(parent);

      for (int i = 0; i < list.size(); i++) {
        if (list.get(i).equals(oldChild)) {
          list.add(i, newChild);
          list.remove(oldChild);
          return oldChild;
        }
      }
    }
    // if oldChild is null or was not found
    return null;
  }

  @PatchMethod
  public static void setNodeValue(Node node, String nodeValue) {
    switch (node.getNodeType()) {
      case Node.DOCUMENT_NODE:
        // nothing to do
        break;
      case Node.ELEMENT_NODE:
        // nothing to do
        break;
      case Node.TEXT_NODE:
        Text text = node.cast();
        text.setData(nodeValue);
        break;
    }
  }

  private static void copyJSOProperties(JavaScriptObject newJso,
      JavaScriptObject oldJso, boolean deep) {
    for (Map.Entry<String, Object> entry : JavaScriptObjects.getJsoProperties(
        oldJso).entrySet()) {

      if (JsoProperties.PARENT_NODE_FIELD.equals(entry.getKey())) {
        // Nothing to do : new cloned node does not have any parent
      } else if (JsoProperties.STYLE_OBJECT_FIELD.equals(entry.getKey())) {
        setupStyle(newJso, (Style) entry.getValue());
      } else if (JsoProperties.NODE_LIST_FIELD.equals(entry.getKey())) {
        Node newNode = newJso.cast();
        Node oldNode = oldJso.cast();
        setupChildNodes(newNode, oldNode, deep);
      } else if (JavaScriptObject.class.isInstance(entry.getValue())) {
        JavaScriptObject oldChildJso = (JavaScriptObject) entry.getValue();
        JavaScriptObject newChildJso = JavaScriptObjects.newObject(oldChildJso.getClass());
        copyJSOProperties(newChildJso, oldChildJso, deep);
        JavaScriptObjects.getJsoProperties(newJso).put(entry.getKey(),
            newChildJso);
      } else {
        // copy the property, which should be a String or a primitive type (or
        // corresponding wrapper object)
        JavaScriptObjects.getJsoProperties(newJso).put(entry.getKey(),
            entry.getValue());
      }
    }

  }

  private static List<Node> getChildNodeList(Node node) {
    NodeList<Node> nodeList = JavaScriptObjects.getJsoProperties(node).getObject(
        JsoProperties.NODE_LIST_FIELD);

    return JavaScriptObjects.getJsoProperties(nodeList).getObject(
        JsoProperties.NODE_LIST_INNER_LIST);
  }

  private static void setupChildNodes(Node newNode, Node oldNode, boolean deep) {

    List<Node> childs = getChildNodeList(oldNode);
    if (deep) {
      // copy all child nodes
      for (Node child : childs) {
        appendChild(newNode, cloneNode(child, true));
      }
    } else {
      // only copy the TextNode if exists
      for (Node child : childs) {
        if (Node.TEXT_NODE == child.getNodeType()) {
          appendChild(newNode,
              Document.get().createTextNode(child.getNodeValue()));
          break;
        }
      }
    }
  }

  private static void setupStyle(JavaScriptObject newNode, Style oldStyle) {
    Style newStyle = JavaScriptObjects.getJsoProperties(newNode).getObject(
        JsoProperties.STYLE_OBJECT_FIELD);

    for (Map.Entry<String, Object> entry : JavaScriptObjects.getJsoProperties(
        oldStyle).entrySet()) {
      if (!JsoProperties.STYLE_TARGET_ELEMENT.equals(entry.getKey())) {
        JavaScriptObjects.getJsoProperties(newStyle).put(entry.getKey(),
            entry.getValue());
      }

    }

  }

}
