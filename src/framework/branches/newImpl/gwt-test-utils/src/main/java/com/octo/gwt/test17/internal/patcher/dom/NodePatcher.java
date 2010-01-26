package com.octo.gwt.test17.internal.patcher.dom;

import java.util.Map;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeFactory;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test17.ArrayUtils;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class NodePatcher extends AutomaticPatcher {

	private static final String NODE_LIST_FIELD = "ChildNodes";
	private static final String PARENT_NODE_FIELD = "ParentNode";
	private static final String OWNER_FIELD = "OwnerDocument";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		
		CtConstructor cons = findConstructor(c);

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(PropertyHolder.callSet(NODE_LIST_FIELD, "new " + OverrideNodeList.class.getCanonicalName() + "()"));
		sb.append(PropertyHolder.callSet(OWNER_FIELD, NodeFactory.class.getCanonicalName() + ".DOCUMENT"));
		sb.append("}");
		cons.setBody(sb.toString());
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
		node = ElementUtils.castToDomElement(node);
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
		parent = ElementUtils.castToDomElement(parent);
		newChild = ElementUtils.castToDomElement(newChild);
		// remove from old parent list if exist
		Node oldParent = newChild.getParentNode();
		if (oldParent != null) {
			oldParent.removeChild(newChild);
		}

		// then, add to the new parent
		OverrideNodeList<Node> list = getChildNodeList(parent);
		list.getList().add(newChild);
		PropertyHolder.get(newChild).put(PARENT_NODE_FIELD, parent);
		return newChild;
	}

	@PatchMethod
	public static Node removeChild(Node oldParent, Node oldChild) {
		oldParent = ElementUtils.castToDomElement(oldParent);
		oldChild = ElementUtils.castToDomElement(oldChild);

		OverrideNodeList<Node> list = getChildNodeList(oldParent);

		if (list.getList().remove(oldChild)) {
			return oldChild;
		} else {
			return null;
		}
	}

	@PatchMethod
	public static Node getFirstChild(Node node) {
		node = ElementUtils.castToDomElement(node);
		OverrideNodeList<Node> list = getChildNodeList(node);

		if (list.getLength() == 0) {
			return null;
		}

		return list.getItem(0);
	}

	@PatchMethod
	public static Node getLastChild(Node node) {
		node = ElementUtils.castToDomElement(node);
		OverrideNodeList<Node> list = getChildNodeList(node);

		if (list.getLength() == 0) {
			return null;
		}

		return list.getItem(list.getLength() - 1);
	}

	@PatchMethod
	public static boolean hasChildNodes(Node node) {
		node = ElementUtils.castToDomElement(node);
		return getChildNodeList(node).getLength() > 0;
	}

	@PatchMethod
	public static Node getNextSibling(Node node) {
		node = ElementUtils.castToDomElement(node);
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
		node = ElementUtils.castToDomElement(node);

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
		parent = ElementUtils.castToDomElement(parent);
		newChild = ElementUtils.castToDomElement(newChild);
		refChild = ElementUtils.castToDomElement(refChild);
		OverrideNodeList<Node> list = getChildNodeList(parent);

		if (refChild != null) {
			for (int i = 0; i < list.getLength(); i++) {
				if (list.getItem(i).equals(refChild)) {
					list.getList().add(i, newChild);
					return newChild;
				}
			}
		}

		// if refChild is null or was not found
		list.getList().add(newChild);
		return newChild;
	}

	@PatchMethod
	public static Node replaceChild(Node parent, Node newChild, Node oldChild) {
		parent = ElementUtils.castToDomElement(parent);
		newChild = ElementUtils.castToDomElement(newChild);
		oldChild = ElementUtils.castToDomElement(oldChild);
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
		node = ElementUtils.castToDomElement(node);
		Map<String, Object> map = ArrayUtils.copyMap(PropertyHolder.get(node));

		map.put(PARENT_NODE_FIELD, null);

		if (!deep) {
			map.put(NODE_LIST_FIELD, new OverrideNodeList<Node>());
		}

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
			newNode = NodeFactory.createText();
		} else if (node instanceof Node) {
			newNode = NodeFactory.createNode();
		} else {
			throw new RuntimeException("Cannot create a Node of type [" + node.getClass().getCanonicalName() + "]");
		}

		PropertyHolder.set(newNode, map);
		return newNode;

	}

	@SuppressWarnings("unchecked")
	private static OverrideNodeList<Node> getChildNodeList(Node node) {
		return (OverrideNodeList<Node>) PropertyHolder.get(node).get(NODE_LIST_FIELD);
	}
}
