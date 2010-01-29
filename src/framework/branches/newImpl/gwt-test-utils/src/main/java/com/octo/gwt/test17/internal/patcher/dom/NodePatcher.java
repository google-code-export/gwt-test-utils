package com.octo.gwt.test17.internal.patcher.dom;

import java.util.Map.Entry;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeFactory;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.ng.AutomaticSubclasser;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.PropertyContainer;
import com.octo.gwt.test17.ng.SubClassedHelper;

public class NodePatcher extends AutomaticSubclasser {

	public static final String NODE_LIST_FIELD = "ChildNodes";
	private static final String PARENT_NODE_FIELD = "ParentNode";

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
		
		SubClassedHelper.setProperty(newChild, PARENT_NODE_FIELD, parent);
		
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
		PropertyContainer propertyContainer = SubClassedHelper.getSubClassedObjectOrNull(node).getOverrideProperties();
		node = ElementUtils.castToDomElement(node);
	
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

		PropertyContainer propertyContainer2 = SubClassedHelper.getSubClassedObjectOrNull(newNode).getOverrideProperties();
		
		propertyContainer2.clear();
		
		fillNewPropertyContainer(propertyContainer2, propertyContainer);
		
		OverrideNodeList<Node> newChilds = new OverrideNodeList<Node>();
		propertyContainer2.put(NODE_LIST_FIELD, newChilds);
		
		if (deep) {
			OverrideNodeList<Node> childs = getChildNodeList(node);
			for(Node child : childs.getList()) {
				appendChild(newNode, cloneNode(child, true));
			}
		}
		return newNode;
	}
	
	private static void fillNewPropertyContainer(PropertyContainer n, PropertyContainer old) {
		for(Entry<String, Object> entry : old.entrySet()) {
			if (PARENT_NODE_FIELD.equals(entry.getKey())) {
			}
			else if (entry.getValue() instanceof String) {
				n.put(entry.getKey(), new String((String) entry.getValue()));
			}
			else if (entry.getValue() instanceof Integer) {
				n.put(entry.getKey(), new Integer((Integer) entry.getValue()));
			}
			else if (entry.getValue() instanceof Double) {
				n.put(entry.getKey(), new Double((Double) entry.getValue()));
			}
			else if (entry.getValue() instanceof Boolean) {
				n.put(entry.getKey(), new Boolean((Boolean) entry.getValue()));
			}
			else if (entry.getValue() instanceof Style) {
				// FIXME cloner
				n.put(entry.getKey(), entry.getValue());
			}
					else if (entry.getValue() instanceof OverrideNodeList<?>) {
			}
			else if (entry.getValue() instanceof PropertyContainer) {
				PropertyContainer nn = new PropertyContainer();
				fillNewPropertyContainer(nn, (PropertyContainer) entry.getValue());
				n.put(entry.getKey(), nn);
			}	
			else {
				throw new RuntimeException("Not managed type " + entry.getValue().getClass() + ", value " + entry.getKey());
			}
		}
	}
	
	private static OverrideNodeList<Node> getChildNodeList(Node node) {
		return SubClassedHelper.getProperty(node, NODE_LIST_FIELD);
	}
}
