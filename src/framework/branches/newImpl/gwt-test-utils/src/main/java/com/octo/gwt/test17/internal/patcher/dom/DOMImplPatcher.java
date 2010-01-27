package com.octo.gwt.test17.internal.patcher.dom;

import java.util.Map;

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
import com.octo.gwt.test17.internal.overrides.OverrideEvent;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.SubClassedObject;

public class DOMImplPatcher extends AutomaticPatcher {

	private static final String NODE_LIST_FIELD = "ChildNodes";

	@PatchMethod
	public static Element createElement(Object domImpl, Document doc, String tag) {
		return NodeFactory.createElement(tag);
	}
	
	@PatchMethod
	public static int getBodyOffsetLeft(Object domImpl, Document doc) {
		return 0;
	}
	
	@PatchMethod
	public static int getBodyOffsetTop(Object domImpl, Document doc) {
		return 0;
	}
	
	@PatchMethod
	public static ButtonElement createButtonElement(Object domImpl, Document doc, String type) {
		ButtonElement e = (ButtonElement) doc.createElement("button");
		SubClassedObject.Helper.setProperty(e, "Type", type);
		return e;
	}
	
	@PatchMethod
	public static InputElement createInputElement(Object domImpl, Document doc, String type) {
		return createInputElement(doc, type, null);
	}

	@PatchMethod
	@SuppressWarnings("unchecked")
	public static String getAttribute(Object domImpl, Element elem, String name) {
		elem = ElementUtils.castToDomElement(elem);
		Map<String, String> attrs = (Map<String, String>) PropertyHolder.get(elem).get(ElementPatcher.PROPERTY_MAP_FIELD);
		return attrs.get(name);
	}

	@PatchMethod
	public static String getInnerHTML(Object domImpl, Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		return (String) PropertyHolder.get(elem).get("InnerHTML");
	}
	
	@PatchMethod
	public static String getInnerText(Object domImpl, Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		return (String) PropertyHolder.get(elem).get("InnerText");
	}

	@PatchMethod
	public static void setInnerText(Object domImpl, Element elem, String text) {
		elem = ElementUtils.castToDomElement(elem);
		PropertyHolder.get(elem).put("InnerText", text);
	}

	@PatchMethod
	public static int getAbsoluteLeft(Object domImpl, Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		Integer absoluteLeft = (Integer) PropertyHolder.get(elem).get("AbsoluteLeft");
		return (absoluteLeft != null) ? absoluteLeft : 0;
	}

	@PatchMethod
	public static int getAbsoluteTop(Object domImpl, Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		Integer absoluteTop = (Integer) PropertyHolder.get(elem).get("AbsoluteTop");
		return (absoluteTop != null) ? absoluteTop : 0;
	}

	@PatchMethod
	public static Element getFirstChildElement(Object domImpl, Element elem) {
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

	@PatchMethod
	public static Element getParentElement(Object domImpl, Node elem) {
		elem = ElementUtils.castToDomElement(elem);
		Node parent = elem.getParentNode();

		if (parent == null || !(parent instanceof Element))
			return null;

		return (Element) parent;
	}

	@PatchMethod
	public static Element getNextSiblingElement(Object domImpl, Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		Node parent = elem.getParentNode();
		if (parent == null)
			return null;

		OverrideNodeList<Node> list = getChildNodeList(domImpl, parent);

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

	@PatchMethod(args={Element.class, Integer.class})
	public static void setScrollLeft(Object domImpl, Element elem, int left) {
		elem = ElementUtils.castToDomElement(elem);
		PropertyHolder.get(elem).put("ScrollLeft", left);
	}

	@PatchMethod(args={Element.class})
	public static int getScrollLeft(Object domImpl, Element elem) {
		elem = ElementUtils.castToDomElement(elem);
		Integer srcollLeft = (Integer) PropertyHolder.get(elem).get("ScrollLeft");
		return (srcollLeft != null) ? srcollLeft : 0;
	}

	@PatchMethod
	public static String eventGetType(Object domImpl, NativeEvent nativeEvent) {
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

	@PatchMethod
	public static int eventGetKeyCode(Object domImpl, NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.getOverrideKeyCode();
	}

	@PatchMethod
	public static int eventGetButton(Object domImpl, NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.getOverrideButton();
	}

	@PatchMethod
	public static boolean eventGetAltKey(Object domImpl, NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.isOverrideAltKey();
	}

	@PatchMethod
	public static boolean eventGetCtrlKey(Object domImpl, NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.isOverrideCtrlKey();
	}

	@PatchMethod
	public static boolean eventGetMetaKey(Object domImpl, NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.isOverrideMetaKey();
	}

	@PatchMethod
	public static boolean eventGetShiftKey(Object domImpl, NativeEvent evt) {
		OverrideEvent event = (OverrideEvent) evt;
		return event.isOverrideShiftKey();
	}

	@PatchMethod
	public static String imgGetSrc(Object domImpl, Element img) {
		return (String) PropertyHolder.get(img).get("Src");
	}

	@PatchMethod
	public static void imgSetSrc(Object domImpl, Element img, String src) {
		PropertyHolder.get(img).put("Src", src);
	}

	@PatchMethod
	public static void selectAdd(Object domImpl, SelectElement select, OptionElement option, OptionElement before) {
		if (before == null) {
			select.appendChild(option);
		} else {
			select.insertBefore(option, before);
		}
	}

	@PatchMethod
	public static NodeList<OptionElement> selectGetOptions(Object domImpl, SelectElement select) {
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
	private static OverrideNodeList<Node> getChildNodeList(Object domImpl, Node node) {
		return (OverrideNodeList<Node>) PropertyHolder.get(node).get(NODE_LIST_FIELD);
	}

	public static InputElement createInputElement(Document doc, String type, String name) {
		InputElement e = (InputElement) doc.createElement("input");
		SubClassedObject.Helper.setProperty(e, "Type", type);
		
		if (name != null) {
			SubClassedObject.Helper.setProperty(e, "Name", name);
		}

		return e;
	}

}
