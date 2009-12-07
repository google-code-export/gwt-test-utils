package com.octo.gwt.test17.internal.patcher;

import java.util.Map;

import javassist.CtMethod;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeFactory;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test17.PatchUtils;
import com.octo.gwt.test17.internal.overrides.OverrideEvent;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;

public class DOMImplPatcher extends AbstractPatcher {

	private static final String NODE_LIST_FIELD = "ChildNodes";

	@Override
	public boolean patchMethod(CtMethod m) throws Exception {
		if ("createElement".equals(m.getName())) {
			PatchUtils.replaceImplementation(m, NodeFactory.class, "createElement", "$2");
		} else if ("createInputElement".equals(m.getName())) {
			replaceImplementation(m, "createInputElement", "$1, $2, null");
		} else if ("createInputRadioElement".equals(m.getName())) {
			replaceImplementation(m, "createInputElement", "$1, \"RADIO\", $2");
		} else if ("getAttribute".equals(m.getName())) {
			replaceImplementation(m, "getAttribute", "$1, $2");
		} else if ("getInnerHTML".equals(m.getName())) {
			replaceImplementation(m, "getInnerHTML", "$1");
		} else if ("getInnerText".equals(m.getName())) {
			replaceImplementation(m, "getInnerText", "$1");
		} else if ("setInnerText".equals(m.getName())) {
			replaceImplementation(m, "setInnerText", "$1, $2");
		} else if ("getFirstChildElement".equals(m.getName())) {
			replaceImplementation(m, "getFirstChildElement", "$1");
		} else if ("getParentElement".equals(m.getName())) {
			replaceImplementation(m, "getParentElement", "$1");
		} else if ("getNextSiblingElement".equals(m.getName())) {
			replaceImplementation(m, "getNextSiblingElement", "$1");
		} else if ("setScrollLeft".equals(m.getName()) && argsMatch(m, new Class[] { Element.class, Integer.TYPE })) {
			replaceImplementation(m, "setScrollLeft", "$1, $2");
		} else if ("getScrollLeft".equals(m.getName()) && argsMatch(m, new Class[] { Element.class })) {
			replaceImplementation(m, "getScrollLeft", "$1");
		} else if ("isOrHasChild".equals(m.getName())) {
			replaceImplementation(m, "isOrHasChild", "$1, $2");
		} else {
			return false;
		}
		return true;
	}

	public static InputElement createInputElement(Document doc, String type, String name) {
		InputElement e = (InputElement) doc.createElement("input");
		PropertyHolder.get(e).put("Type", type);

		if (name != null) {
			PropertyHolder.get(e).put("Name", name);
		}

		return e;
	}

	@SuppressWarnings("unchecked")
	public static String getAttribute(Element elem, String name) {
		Map<String, String> attrs = (Map<String, String>) PropertyHolder.get(elem).get(ElementPatcher.ATTRIBUTS_MAP_FIELD);
		return attrs.get(name);
	}

	public static String getInnerHTML(Element elem) {
		return (String) PropertyHolder.get(elem).get("InnerHTML");
	}

	public static String getInnerText(Element elem) {
		return (String) PropertyHolder.get(elem).get("InnerText");
	}

	public static void setInnerText(Element elem, String text) {
		PropertyHolder.get(elem).put("InnerText", text);
	}

	public static Element getFirstChildElement(Element elem) {
		NodeList<Node> nodeList = elem.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.getItem(i);
			if (node instanceof Element) {
				return (Element) node;
			}
		}

		return null;
	}

	public static Element getParentElement(Element elem) {
		Node parent = elem.getParentNode();

		if (parent == null || !(parent instanceof Element))
			return null;

		return (Element) parent;
	}

	public static Element getNextSiblingElement(Element elem) {
		Node parent = elem.getParentNode();
		if (parent == null)
			return null;

		OverrideNodeList<Node> list = getChildNodeList(parent);

		for (int i = 0; i < list.getLength(); i++) {
			Node current = list.getItem(i);
			if (current.equals(elem) && i < list.getLength() - 1) {
				while (i < list.getLength() - 1) {
					i++;
					if (list.getItem(i) instanceof Element) {
						return (Element) list.getItem(i);
					}
				}
			}
		}

		return null;
	}

	public static void setScrollLeft(Element elem, int left) {
		PropertyHolder.get(elem).put("ScrollLeft", left);
	}

	public static int getScrollLeft(Element elem) {
		return (Integer) PropertyHolder.get(elem).get("ScrollLeft");
	}

	public static boolean isOrHasChild(Element parent, Element child) {
		if (parent.equals(child)) {
			return true;
		} else if (child.getParentElement() != null && child.getParentElement().equals(parent)) {
			return true;
		}
		return false;
	}

	public static String eventGetType(NativeEvent nativeEvent) {
		OverrideEvent event = OverrideEvent.overrideCast(nativeEvent);
		switch (event.getOverrideType()) {
		case Event.ONBLUR:
			return "blur";
		case Event.ONCHANGE:
			return "change";
		case Event.ONCLICK:
			return "click";
		case Event.ONDBLCLICK:
			return "dblclick";
		case Event.ONFOCUS:
			return "focus";
		case Event.ONKEYDOWN:
			return "keydown";
		case Event.ONKEYPRESS:
			return "keypress";
		case Event.ONKEYUP:
			return "keyup";
		case Event.ONLOAD:
			return "load";
		case Event.ONLOSECAPTURE:
			return "losecapture";
		case Event.ONMOUSEDOWN:
			return "mousedown";
		case Event.ONMOUSEMOVE:
			return "mousemove";
		case Event.ONMOUSEOUT:
			return "mouseout";
		case Event.ONMOUSEOVER:
			return "mouseover";
		case Event.ONMOUSEUP:
			return "mouseup";
		case Event.ONSCROLL:
			return "scroll";
		case Event.ONERROR:
			return "error";
		case Event.ONMOUSEWHEEL:
			return "mousewheel";
		case Event.ONCONTEXTMENU:
			return "contextmenu";
		default:
			throw new RuntimeException("Cannot get the String type of event with code [" + event.getOverrideType() + "]");
		}
	}

	public static int eventGetTypeInt(String type) {
		if (type.equals("blur")) {
			return Event.ONBLUR;
		} else if (type.equals("change")) {
			return Event.ONCHANGE;
		} else if (type.equals("click")) {
			return Event.ONCLICK;
		} else if (type.equals("dblclick")) {
			return Event.ONDBLCLICK;
		} else if (type.equals("focus")) {
			return Event.ONFOCUS;
		} else if (type.equals("keydown")) {
			return Event.ONKEYDOWN;
		} else if (type.equals("keypress")) {
			return Event.ONKEYPRESS;
		} else if (type.equals("keyup")) {
			return Event.ONKEYUP;
		} else if (type.equals("load")) {
			return Event.ONLOAD;
		} else if (type.equals("losecapture")) {
			return Event.ONLOSECAPTURE;
		} else if (type.equals("mousedown")) {
			return Event.ONMOUSEDOWN;
		} else if (type.equals("mousemove")) {
			return Event.ONMOUSEMOVE;
		} else if (type.equals("mouseout")) {
			return Event.ONMOUSEOUT;
		} else if (type.equals("mouseover")) {
			return Event.ONMOUSEOVER;
		} else if (type.equals("mouseup")) {
			return Event.ONMOUSEUP;
		} else if (type.equals("scroll")) {
			return Event.ONSCROLL;
		} else if (type.equals("error")) {
			return Event.ONERROR;
		} else if (type.equals("mousewheel")) {
			return Event.ONMOUSEWHEEL;
		} else if (type.equals("contextmenu")) {
			return Event.ONCONTEXTMENU;
		}

		throw new RuntimeException("Unable to convert DOM Event \"" + type + "\" to an integer");
	}

	@SuppressWarnings("unchecked")
	private static OverrideNodeList<Node> getChildNodeList(Node node) {
		return (OverrideNodeList<Node>) PropertyHolder.get(node).get(NODE_LIST_FIELD);
	}

}
