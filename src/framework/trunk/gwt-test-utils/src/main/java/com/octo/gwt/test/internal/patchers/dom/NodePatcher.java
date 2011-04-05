package com.octo.gwt.test.internal.patchers.dom;

import java.util.Map.Entry;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.exceptions.GwtTestDomException;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Node.class)
public class NodePatcher extends OverlayPatcher {

  public static final String NODE_LIST_FIELD = "ChildNodes";
  public static final String PARENT_NODE_FIELD = "ParentNode";

  @PatchMethod
  public static Node appendChild(Node parent, Node newChild) {
    return insertAtIndex(parent, newChild, -1);
  }

  @PatchMethod
  public static Node cloneNode(Node node, boolean deep) {
    PropertyContainer propertyContainer = PropertyContainerUtils.cast(node).getProperties();

    Node newNode;
    switch (node.getNodeType()) {
      case Node.ELEMENT_NODE:
        Element elem = node.cast();
        newNode = NodeFactory.createElement((elem).getTagName());
        break;
      case Node.DOCUMENT_NODE:
        newNode = NodeFactory.getDocument();
        break;
      case Node.TEXT_NODE:
        newNode = NodeFactory.createTextNode(((Text) node).getData());
        break;
      default:
        throw new GwtTestDomException("Cannot create a Node of type ["
            + node.getClass().getName() + "]");
    }

    PropertyContainer propertyContainer2 = PropertyContainerUtils.cast(newNode).getProperties();

    propertyContainer2.clear();

    fillNewPropertyContainer(propertyContainer2, propertyContainer);

    OverrideNodeList<Node> newChilds = new OverrideNodeList<Node>();
    propertyContainer2.put(NODE_LIST_FIELD, newChilds);

    OverrideNodeList<Node> childs = getChildNodeList(node);
    if (deep) {
      // copy all child nodes
      for (Node child : childs.getList()) {
        appendChild(newNode, cloneNode(child, true));
      }
    } else {
      // only copy the TextNode if exists
      for (Node child : childs.getList()) {
        if (Node.TEXT_NODE == child.getNodeType()) {
          appendChild(newNode,
              Document.get().createTextNode(child.getNodeValue()));
          break;
        }
      }
    }
    return newNode;
  }

  @PatchMethod
  public static Node getFirstChild(Node node) {
    OverrideNodeList<Node> list = getChildNodeList(node);

    if (list.getLength() == 0) {
      return null;
    }

    return list.getItem(0);
  }

  @PatchMethod
  public static Node getLastChild(Node node) {
    OverrideNodeList<Node> list = getChildNodeList(node);

    if (list.getLength() == 0) {
      return null;
    }

    return list.getItem(list.getLength() - 1);
  }

  @PatchMethod
  public static Node getNextSibling(Node node) {
    Node parent = node.getParentNode();
    if (parent == null)
      return null;

    OverrideNodeList<Node> list = getChildNodeList(parent);

    for (int i = 0; i < list.getLength(); i++) {
      Node current = list.getItem(i);
      if (current.equals(node) && i < list.getLength() - 1) {
        return list.getItem(i + 1);
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
      default:
        throw new GwtTestDomException(
            "Invalid Node type (not a Document / Element / Text : "
                + node.getNodeType());
    }
  }

  @PatchMethod
  public static short getNodeType(Node node) {
    if (node instanceof Document) {
      return Node.DOCUMENT_NODE;
    } else if (node instanceof Text) {
      return Node.TEXT_NODE;
    } else if (node instanceof Element) {
      return Node.ELEMENT_NODE;
    }

    return (short) -1;
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
    return NodeFactory.getDocument();
  }

  @PatchMethod
  public static Node getPreviousSibling(Node node) {
    Node parent = node.getParentNode();
    if (parent == null)
      return null;

    OverrideNodeList<Node> list = getChildNodeList(parent);

    for (int i = 0; i < list.getLength(); i++) {
      Node current = list.getItem(i);
      if (current.equals(node) && i > 0) {
        return list.getItem(i - 1);
      }
    }

    return null;
  }

  @PatchMethod
  public static boolean hasChildNodes(Node node) {
    return getChildNodeList(node).getLength() > 0;
  }

  public static Node insertAtIndex(Node parent, Node newChild, int index) {
    OverrideNodeList<Node> list = getChildNodeList(parent);

    // First, remove from old parent
    Node oldParent = newChild.getParentNode();
    if (oldParent != null) {
      oldParent.removeChild(newChild);
    }

    // Then, check parent doesn't contain newChild and remove it if necessary
    list.getList().remove(newChild);

    // Finally, add
    if (index == -1 || index >= list.getLength()) {
      list.getList().add(newChild);
    } else {
      list.getList().add(index, newChild);
    }

    // Manage getParentNode()
    PropertyContainerUtils.setProperty(newChild, PARENT_NODE_FIELD, parent);

    return newChild;
  }

  @PatchMethod
  public static Node insertBefore(Node parent, Node newChild, Node refChild) {
    OverrideNodeList<Node> list = getChildNodeList(parent);

    // get the index of refChild
    int index = -1;
    if (refChild != null) {
      int i = 0;
      while (index == -1 && i < list.getLength()) {
        if (list.getItem(i).equals(refChild)) {
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
    OverrideNodeList<Node> list = getChildNodeList(oldParent);

    if (list.getList().remove(oldChild)) {
      return oldChild;
    } else {
      return null;
    }
  }

  @PatchMethod
  public static Node replaceChild(Node parent, Node newChild, Node oldChild) {
    if (oldChild != null) {
      OverrideNodeList<Node> list = getChildNodeList(parent);

      for (int i = 0; i < list.getLength(); i++) {
        if (list.getItem(i).equals(oldChild)) {
          list.getList().add(i, newChild);
          list.getList().remove(oldChild);
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

  private static void fillNewPropertyContainer(PropertyContainer n,
      PropertyContainer old) {
    for (Entry<String, Object> entry : old.entrySet()) {
      if (PARENT_NODE_FIELD.equals(entry.getKey())) {
      } else if (entry.getValue() instanceof String) {
        n.put(entry.getKey(), new String((String) entry.getValue()));
      } else if (entry.getValue() instanceof Integer) {
        n.put(entry.getKey(), new Integer((Integer) entry.getValue()));
      } else if (entry.getValue() instanceof Double) {
        n.put(entry.getKey(), new Double((Double) entry.getValue()));
      } else if (entry.getValue() instanceof Boolean) {
        n.put(entry.getKey(), new Boolean((Boolean) entry.getValue()));
      } else if (entry.getValue() instanceof Style) {
        // The propertyContainerAware have to be an instance of Element since
        // Style requiers Element in its constructor with gwt-test-utils
        Style newStyle = NodeFactory.createStyle((Element) n.getOwner());
        PropertyContainer o = PropertyContainerUtils.cast(entry.getValue()).getProperties();
        PropertyContainer nn = PropertyContainerUtils.cast(newStyle).getProperties();
        nn.clear();

        fillNewPropertyContainer(nn, o);
        n.put(entry.getKey(), newStyle);
      } else if (entry.getValue() instanceof OverrideNodeList<?>) {
      } else if (entry.getValue() instanceof PropertyContainer) {
        PropertyContainer toCopy = (PropertyContainer) entry.getValue();
        PropertyContainer nn = new PropertyContainer(toCopy.getOwner());
        fillNewPropertyContainer(nn, toCopy);
        n.put(entry.getKey(), nn);
      } else {
        throw new GwtTestDomException("Not managed type "
            + entry.getValue().getClass() + ", value " + entry.getKey());
      }
    }
  }

  private static OverrideNodeList<Node> getChildNodeList(Node node) {
    return PropertyContainerUtils.getProperty(node, NODE_LIST_FIELD);
  }

  @Override
  public void initClass(CtClass c) throws Exception {
    super.initClass(c);
    CtConstructor cons = findConstructor(c);

    cons.insertAfter(PropertyContainerUtils.getCodeSetProperty("this",
        NodePatcher.NODE_LIST_FIELD,
        "new " + OverrideNodeList.class.getCanonicalName() + "()")
        + ";");
  }
}
