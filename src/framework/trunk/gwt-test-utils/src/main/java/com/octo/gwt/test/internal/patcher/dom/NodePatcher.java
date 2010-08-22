package com.octo.gwt.test.internal.patcher.dom;

import java.util.Map.Entry;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.patcher.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.patcher.PropertyContainer;
import com.octo.gwt.test.patcher.PropertyContainerHelper;

@PatchClass(Node.class)
public class NodePatcher extends AutomaticPropertyContainerPatcher {

	public static final String NODE_LIST_FIELD = "ChildNodes";
	public static final String PARENT_NODE_FIELD = "ParentNode";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);

		cons.insertAfter(PropertyContainerHelper.getCodeSetProperty("this", NodePatcher.NODE_LIST_FIELD,
				"new " + OverrideNodeList.class.getCanonicalName() + "()", true)
				+ ";");
	}

	@PatchMethod
	public static Document getOwnerDocument(Node node) {
		return NodeFactory.getDocument();
	}

	@PatchMethod
	public static boolean is(JavaScriptObject object) {
		if (object == null || !(object instanceof Node)) {
			return false;
		}

		return true;
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
	public static Node appendChild(Node parent, Node newChild) {
		return insertAtIndex(parent, newChild, -1);
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
	public static boolean hasChildNodes(Node node) {
		return getChildNodeList(node).getLength() > 0;
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
	public static Node cloneNode(Node node, boolean deep) {
		PropertyContainer propertyContainer = PropertyContainerHelper.cast(node).getProperties();

		Node newNode;
		if (node instanceof Element) {
			try {
				newNode = NodeFactory.createElement(((Element) node).getTagName());
			} catch (Exception e) {
				throw new RuntimeException("Error while creating an element of type [" + node.getClass().getName() + "]");
			}
		} else if (node instanceof Document) {
			newNode = NodeFactory.getDocument();
		} else if (node instanceof Text) {
			newNode = NodeFactory.createTextNode(((Text) node).getData());
		} else if (node instanceof Node) {
			newNode = NodeFactory.createNode();
		} else {
			throw new RuntimeException("Cannot create a Node of type [" + node.getClass().getCanonicalName() + "]");
		}

		PropertyContainer propertyContainer2 = PropertyContainerHelper.cast(newNode).getProperties();

		propertyContainer2.clear();

		fillNewPropertyContainer(propertyContainer2, propertyContainer);

		OverrideNodeList<Node> newChilds = new OverrideNodeList<Node>();
		propertyContainer2.put(NODE_LIST_FIELD, newChilds);

		if (deep) {
			OverrideNodeList<Node> childs = getChildNodeList(node);
			for (Node child : childs.getList()) {
				appendChild(newNode, cloneNode(child, true));
			}
		}
		return newNode;
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
		PropertyContainerHelper.setProperty(newChild, PARENT_NODE_FIELD, parent);

		return newChild;
	}

	private static void fillNewPropertyContainer(PropertyContainer n, PropertyContainer old) {
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
				Style newStyle = NodeFactory.createStyle();
				PropertyContainer o = PropertyContainerHelper.cast(entry.getValue()).getProperties();
				PropertyContainer nn = PropertyContainerHelper.cast(newStyle).getProperties();
				nn.clear();

				fillNewPropertyContainer(nn, o);
				n.put(entry.getKey(), newStyle);
			} else if (entry.getValue() instanceof OverrideNodeList<?>) {
			} else if (entry.getValue() instanceof PropertyContainer) {
				PropertyContainer nn = new PropertyContainer();
				fillNewPropertyContainer(nn, (PropertyContainer) entry.getValue());
				n.put(entry.getKey(), nn);
			} else {
				throw new RuntimeException("Not managed type " + entry.getValue().getClass() + ", value " + entry.getKey());
			}
		}
	}

	private static OverrideNodeList<Node> getChildNodeList(Node node) {
		return PropertyContainerHelper.getProperty(node, NODE_LIST_FIELD);
	}
}
