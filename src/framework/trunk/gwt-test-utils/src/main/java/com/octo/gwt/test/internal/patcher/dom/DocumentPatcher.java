package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.patcher.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.patcher.PropertyContainerHelper;

@PatchClass(Document.class)
public class DocumentPatcher extends AutomaticPropertyContainerPatcher {

	private static int ID = 0;

	public static final String BODY_PROPERTY = "Body";

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
		Element e = document.getDocumentElement();
		return PropertyContainerHelper.getProperty(e, NodeFactory.BODY_ELEMENT);
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
			if (Element.class.isInstance(n) && elementId.equals(((Element) n).getId())) {
				return (Element) n;
			}
			Element result = getElementById(n, elementId);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	@PatchMethod
	public static NodeList<Element> getElementsByTagName(Document document, String tagName) {
		OverrideNodeList<Element> result = new OverrideNodeList<Element>();

		inspectDomForTag(document, tagName, result);

		return result;
	}

	private static void inspectDomForTag(Node node, String tagName, OverrideNodeList<Element> result) {
		OverrideNodeList<Node> childs = getChildNodeList(node);
		for (Node n : childs.getList()) {
			if (Element.class.isInstance(n) && tagName.equals(((Element) n).getTagName())) {
				result.getList().add((Element) n);
			}
			inspectDomForTag(n, tagName, result);
		}
	}

	private static OverrideNodeList<Node> getChildNodeList(Node node) {
		return PropertyContainerHelper.getProperty(node, "ChildNodes");
	}

}
