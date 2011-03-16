package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.patchers.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Document.class)
public class DocumentPatcher extends AutomaticPropertyContainerPatcher {

	private static int ID = 0;

	@PatchMethod
	public static String getCompatMode(Document document) {
		return "toto";
	}

	@PatchMethod
	public static Document get() {
		return NodeFactory.getDocument();
	}

	@PatchMethod
	public static Text createTextNode(Document document, String data) {
		return NodeFactory.createTextNode(data);
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
	public static String getDomain(Document document) {
		return null;
	}

	@PatchMethod
	public static String getReferrer(Document document) {
		return "";
	}

	@PatchMethod
	public static String createUniqueId(Document document) {
		ID++;
		return "elem_" + Long.toString(ID);
	}

	@PatchMethod
	public static Element getElementById(Node document, String elementId) {
		OverrideNodeList<Node> childs = getChildNodeList(document);
		for (Node n : childs.getList()) {
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
		OverrideNodeList<Element> result = new OverrideNodeList<Element>();

		inspectDomForTag(node, tagName, result);

		return result;
	}

	private static void inspectDomForTag(Node node, String tagName, OverrideNodeList<Element> result) {
		OverrideNodeList<Node> childs = getChildNodeList(node);
		for (Node n : childs.getList()) {
			if (Node.ELEMENT_NODE == n.getNodeType()) {
				Element childElem = n.cast();
				if ("*".equals(tagName) || tagName.equalsIgnoreCase(childElem.getTagName())) {
					result.getList().add((Element) n);
				}
			}
			inspectDomForTag(n, tagName, result);
		}
	}

	private static OverrideNodeList<Node> getChildNodeList(Node node) {
		return PropertyContainerUtils.getProperty(node, "ChildNodes");
	}

}
