package com.octo.gwt.test17.internal.patcher.dom;

import javassist.CtMethod;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.internal.patcher.AbstractPatcher;

public class DOMImplUserPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "setEventListener|sinkEvents")) {
			return "";
		} else if (matchWithArgs(m, "eventGetTypeInt", String.class)) {
			return callMethod("eventGetTypeInt", "$1");
		} else if (match(m, "insertChild")) {
			return callMethod("insertChild", "$1, $2, $3");
		} else if (match(m, "getChild")) {
			return callMethod("getChild", "$1, $2");
		} else if (match(m, "getEventsSunk")) {
			return "return 1;";
		} else if (match(m, "getChildIndex")) {
			return callMethod("getChildIndex", "$1, $2");
		}

		return null;
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

	public static void insertChild(Element userParent, Element userChild, int index) {
		com.google.gwt.dom.client.Element parent = ElementUtils.castToDomElement(userParent);
		com.google.gwt.dom.client.Element child = ElementUtils.castToDomElement(userChild);
		OverrideNodeList<Node> nodeList = (OverrideNodeList<Node>) parent.getChildNodes();
		if (index >= nodeList.getLength()) {
			nodeList.getList().add(child);
		} else {
			nodeList.getList().add(index, child);
		}
	}

	public static Element getChild(Element userElem, int index) {
		com.google.gwt.dom.client.Element elem = ElementUtils.castToDomElement(userElem);
		if (index >= elem.getChildNodes().getLength()) {
			return null;
		}

		return ElementUtils.castToUserElement((com.google.gwt.dom.client.Element) elem.getChildNodes().getItem(index));
	}

	public static int getChildIndex(Element parent, Element child) {
		if (parent == null || child == null) {
			return -1;
		}

		parent = ElementUtils.castToDomElement(parent);
		com.google.gwt.dom.client.Element domElem = ElementUtils.castToDomElement(child);

		for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
			Element current = ElementUtils.castToDomElement(parent.getChildNodes().getItem(i));
			if (domElem.equals(current)) {
				return i;
			}
		}

		return -1;
	}
}
