package com.octo.gwt.test17.internal.patcher.dom;

import java.util.Map;

import javassist.CtMethod;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeFactory;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.PatchUtils;
import com.octo.gwt.test17.internal.overrides.OverrideEvent;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.internal.patcher.AbstractPatcher;
import com.octo.gwt.test17.ng.AutomaticSubclasser;

public class DOMImplPatcher extends AbstractPatcher {

	private static final String NODE_LIST_FIELD = "ChildNodes";

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "createElement")) {
			return PatchUtils.callMethod(NodeFactory.class, "createElement", "$2");
		} else if (match(m, "createButtonElement")) {
			return callMethod("createButtonElement", "$1, $2");
		} else if (match(m, "createInputElement")) {
			return callMethod("createInputElement", "$1, $2, null");
		} else if (match(m, "createInputRadioElement")) {
			return callMethod("createInputElement", "$1, \"RADIO\", $2");
		} else if (match(m, "getAttribute")) {
			return callMethod("getAttribute", "$1, $2");
		} else if (match(m, "getInnerHTML")) {
			return callMethod("getInnerHTML", "$1");
		} else if (match(m, "getInnerText")) {
			return callMethod("getInnerText", "$1");
		} else if (match(m, "setInnerText")) {
			return callMethod("setInnerText", "$1, $2");
		} else if (match(m, "getAbsoluteLeft")) {
			return callMethod("getAbsoluteLeft", "$1");
		} else if (match(m, "getAbsoluteTop")) {
			return callMethod("getAbsoluteTop", "$1");
		} else if (match(m, "getFirstChildElement")) {
			return callMethod("getFirstChildElement", "$1");
		} else if (match(m, "getParentElement")) {
			return callMethod("getParentElement", "$1");
		} else if (match(m, "getNextSiblingElement")) {
			return callMethod("getNextSiblingElement", "$1");
		} else if (matchWithArgs(m, "setScrollLeft", Element.class, Integer.TYPE)) {
			return callMethod("setScrollLeft", "$1, $2");
		} else if (matchWithArgs(m, "getScrollLeft", Element.class)) {
			return callMethod("getScrollLeft", "$1");
		} else if (match(m, "isOrHasChild")) {
			return callMethod("isOrHasChild", "$1, $2");
		} else if (match(m, "eventGetType")) {
			return callMethod("eventGetType", "$1");
		} else if (match(m, "eventGetKeyCode")) {
			return callMethod("eventGetKeyCode", "$1");
		} else if (match(m, "eventGetButton")) {
			return callMethod("eventGetButton", "$1");
		} else if (match(m, "eventGetAltKey")) {
			return callMethod("eventGetAltKey", "$1");
		} else if (match(m, "eventGetCtrlKey")) {
			return callMethod("eventGetCtrlKey", "$1");
		} else if (match(m, "eventGetMetaKey")) {
			return callMethod("eventGetMetaKey", "$1");
		} else if (match(m, "eventGetShiftKey")) {
			return callMethod("eventGetShiftKey", "$1");
		} else if (match(m, "eventPreventDefault")) {
			return "";
		} else if (match(m, "imgGetSrc")) {
			return callMethod("imgGetSrc", "$1");
		} else if (match(m, "imgSetSrc")) {
			return callMethod("imgSetSrc", "$1, $2");
		} else if (match(m, "selectAdd")) {
			return callMethod("selectAdd", "$1, $2, $3");
		} else if (match(m, "selectGetOptions")) {
			return callMethod("selectGetOptions", "$1");
		} else if (match(m, "eventGetTarget")) {
			return "return null";
		} else if (match(m, "getBodyOffsetLeft")) {
			return "return 0";
		} else if (match(m, "getBodyOffsetTop")) {
			return "return 0";
		}
		return null;
	}

	public static ButtonElement createButtonElement(Document doc, String type) {
		ButtonElement e = (ButtonElement) doc.createElement("button");
		AutomaticSubclasser.setProperty(e, "Type", type);
		return e;
	}

	public static InputElement createInputElement(Document doc, String type, String name) {
		InputElement e = (InputElement) doc.createElement("input");
		AutomaticSubclasser.setProperty(e, "Type", type);
		
		if (name != null) {
			AutomaticSubclasser.setProperty(e, "Name", name);
		}

		return e;
	}

	@SuppressWarnings("unchecked")
	public static String getAttribute(Element elem, String name) {
		elem = ElementUtils.castToDomElement(elem);
		Map<String, String> attrs = (Map<String, String>) PropertyHolder.get(elem).get(ElementPatcher.PROPERTY_MAP_FIELD);
		return attrs.get(name);
	}

	public static String getInnerHTML(Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		return (String) PropertyHolder.get(elem).get("InnerHTML");
	}
	
	public static String getInnerText(Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		return (String) PropertyHolder.get(elem).get("InnerText");
	}

	public static void setInnerText(Element elem, String text) {
		elem = ElementUtils.castToDomElement(elem);
		PropertyHolder.get(elem).put("InnerText", text);
	}

	public static int getAbsoluteLeft(Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		Integer absoluteLeft = (Integer) PropertyHolder.get(elem).get("AbsoluteLeft");
		return (absoluteLeft != null) ? absoluteLeft : 0;
	}

	public static int getAbsoluteTop(Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		Integer absoluteTop = (Integer) PropertyHolder.get(elem).get("AbsoluteTop");
		return (absoluteTop != null) ? absoluteTop : 0;
	}

	public static Element getFirstChildElement(Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		NodeList<Node> nodeList = elem.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.getItem(i);
			if (node instanceof Element) {
				return (Element) node;
			}
		}

		return null;
	}

	public static Element getParentElement(Node elem) {
		elem = ElementUtils.castToDomElement(elem);
		Node parent = elem.getParentNode();

		if (parent == null || !(parent instanceof Element))
			return null;

		return (Element) parent;
	}

	public static Element getNextSiblingElement(Element elem) {
		elem = ElementUtils.castToDomElement(elem);
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
		elem = ElementUtils.castToDomElement(elem);
		PropertyHolder.get(elem).put("ScrollLeft", left);
	}

	public static int getScrollLeft(Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		Integer srcollLeft = (Integer) PropertyHolder.get(elem).get("ScrollLeft");
		return (srcollLeft != null) ? srcollLeft : 0;
	}

	public static boolean isOrHasChild(Node parent, Node child) {
		parent = ElementUtils.castToDomElement(parent);
		child = ElementUtils.castToDomElement(child);
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

	public static int eventGetKeyCode(NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.getOverrideKeyCode();
	}

	public static int eventGetButton(NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.getOverrideButton();
	}

	public static boolean eventGetAltKey(NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.isOverrideAltKey();
	}

	public static boolean eventGetCtrlKey(NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.isOverrideCtrlKey();
	}

	public static boolean eventGetMetaKey(NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.isOverrideMetaKey();
	}

	public static boolean eventGetShiftKey(NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.isOverrideShiftKey();
	}

	public static String imgGetSrc(Element img) {
		return (String) PropertyHolder.get(img).get("Src");
	}

	public static void imgSetSrc(Element img, String src) {
		PropertyHolder.get(img).put("Src", src);
	}

	public static void selectAdd(SelectElement select, OptionElement option, OptionElement before) {
		if (before == null) {
			select.appendChild(option);
		} else {
			select.insertBefore(option, before);
		}
	}

	public static NodeList<OptionElement> selectGetOptions(SelectElement select) {
		OverrideNodeList<OptionElement> list = new OverrideNodeList<OptionElement>();

		for (int i = 0; i < select.getChildNodes().getLength(); i++) {
			Element e = ElementUtils.castToDomElement(select.getChildNodes().getItem(i));
			if (e instanceof OptionElement) {
				list.getList().add((OptionElement) e);
			}
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	private static OverrideNodeList<Node> getChildNodeList(Node node) {
		return (OverrideNodeList<Node>) PropertyHolder.get(node).get(NODE_LIST_FIELD);
	}

}
