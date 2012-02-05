package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Document.class)
public class DocumentPatcher {

	private static int ID = 0;

	@PatchMethod
	public static Text createTextNode(Document document, String data) {
		return NodeFactory.createTextNode(data);
	}

	@PatchMethod
	public static String createUniqueId(Document document) {
		ID++;
		return "elem_" + Long.toString(ID);
	}

	@PatchMethod
	public static Document get() {
		return NodeFactory.getDocument();
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
	public static NodeList<Element> getElementsByTagName(Document document,
			String tagName) {
		OverrideNodeList<Element> result = new OverrideNodeList<Element>();

		inspectDomForTag(document, tagName, result);

		return result;
	}

	@PatchMethod
	public static String getReferrer(Document document) {
		return "";
	}

	private static OverrideNodeList<Node> getChildNodeList(Node node) {
		return PropertyContainerHelper.getObject(node, "ChildNodes");
	}

	private static void inspectDomForTag(Node node, String tagName,
			OverrideNodeList<Element> result) {
		OverrideNodeList<Node> childs = getChildNodeList(node);
		for (Node n : childs.getList()) {
			if (Element.class.isInstance(n)
					&& tagName.equals(((Element) n).getTagName())) {
				result.getList().add((Element) n);
			}
			inspectDomForTag(n, tagName, result);
		}
	}

}
