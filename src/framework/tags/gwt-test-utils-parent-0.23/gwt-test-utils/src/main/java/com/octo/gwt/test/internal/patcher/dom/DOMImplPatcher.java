package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.internal.utils.TagAware;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.events.EventBuilder;
import com.octo.gwt.test.utils.events.EventUtils;
import com.octo.gwt.test.utils.events.OverrideEvent;

@PatchClass(classes = { "com.google.gwt.dom.client.DOMImpl" })
public class DOMImplPatcher extends AutomaticPatcher {

	private static final String SRC = "Src";
	private static final String SCROLL_LEFT = "ScrollLeft";
	private static final String ABSOLUTE_TOP = "AbsoluteTop";
	private static final String INNER_HTML = "InnerHTML";
	private static final String ABSOLUTE_LEFT = "AbsoluteLeft";
	private static final String INNER_TEXT = "InnerText";
	private static final String TAB_INDEX = "TabIndex";
	private static final String TAG_NAME = "TagName";
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
		PropertyContainerHelper.setProperty(e, "Type", type);
		return e;
	}

	@PatchMethod
	public static InputElement createInputElement(Object domImpl, Document doc, String type) {
		return createInputElement(doc, type, null);
	}

	@PatchMethod
	public static String getAttribute(Object domImpl, Element elem, String name) {
		PropertyContainer propertyContainer = PropertyContainerHelper.getProperty(elem, ElementPatcher.PROPERTY_MAP_FIELD);
		return (String) propertyContainer.get(name);
	}

	@PatchMethod
	public static String getTagName(Object domImpl, Element elem) {
		if (elem == null)
			return null;

		if (elem instanceof TagAware) {
			return ((TagAware) elem).getTag();
		}

		String tagName = PropertyContainerHelper.getProperty(elem, TAG_NAME);
		return (tagName != null) ? tagName : (String) GwtTestReflectionUtils.getStaticFieldValue(elem.getClass(), "TAG");
	}

	@PatchMethod
	public static String getInnerHTML(Object domImpl, Element elem) {
		return PropertyContainerHelper.getProperty(elem, INNER_HTML);
	}

	@PatchMethod
	public static String getInnerText(Object domImpl, Element elem) {
		return PropertyContainerHelper.getProperty(elem, INNER_TEXT);
	}

	@PatchMethod
	public static void setInnerText(Object domImpl, Element elem, String text) {
		PropertyContainerHelper.setProperty(elem, INNER_TEXT, text);
	}

	@PatchMethod
	public static int getAbsoluteLeft(Object domImpl, Element elem) {
		return PropertyContainerHelper.getPropertyInteger(elem, ABSOLUTE_LEFT);
	}

	@PatchMethod
	public static int getAbsoluteTop(Object domImpl, Element elem) {
		return PropertyContainerHelper.getPropertyInteger(elem, ABSOLUTE_TOP);
	}

	@PatchMethod
	public static int getTabIndex(Object domImpl, Element elem) {
		return PropertyContainerHelper.getPropertyInteger(elem, TAB_INDEX);
	}

	@PatchMethod
	public static Element getFirstChildElement(Object domImpl, Element elem) {
		NodeList<Node> nodeList = elem.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.getItem(i);
			if (node instanceof Element) {
				return node.cast();
			}
		}

		return null;
	}

	@PatchMethod
	public static Element getParentElement(Object domImpl, Node elem) {
		Node parent = elem.getParentNode();

		if (parent == null || !(parent instanceof Element))
			return null;

		return parent.cast();
	}

	@PatchMethod
	public static Element getNextSiblingElement(Object domImpl, Element elem) {
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
						return list.getItem(i).cast();
					}
				}
			}
		}

		return null;
	}

	@PatchMethod
	public static void setScrollLeft(Object domImpl, Element elem, int left) {
		PropertyContainerHelper.setProperty(elem, SCROLL_LEFT, left);
	}

	@PatchMethod
	public static int getScrollLeft(Object domImpl, Element elem) {
		return PropertyContainerHelper.getPropertyInteger(elem, SCROLL_LEFT);
	}

	@PatchMethod
	public static String eventGetType(Object domImpl, NativeEvent nativeEvent) {
		return EventUtils.getEventTypeString(nativeEvent);
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
		return PropertyContainerHelper.getProperty(img, SRC);
	}

	@PatchMethod
	public static void imgSetSrc(Object domImpl, Element img, String src) {
		PropertyContainerHelper.setProperty(img, SRC, src);
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
			Element e = (Element) select.getChildNodes().getItem(i).cast();
			if (e instanceof OptionElement) {
				list.getList().add((OptionElement) e);
			}
		}

		return list;
	}

	@PatchMethod
	public static void selectClear(Object domImpl, SelectElement select) {
		OverrideNodeList<Node> childNodes = (OverrideNodeList<Node>) select.getChildNodes();
		childNodes.getList().clear();
		select.setSelectedIndex(-1);
	}

	@PatchMethod
	public static void selectRemoveOption(Object domImpl, SelectElement select, int index) {
		OverrideNodeList<Node> childNodes = (OverrideNodeList<Node>) select.getChildNodes();
		childNodes.getList().remove(index);
	}

	@PatchMethod
	public static void cssSetOpacity(Object domImpl, Style style, double value) {
		String opacityField = GwtTestReflectionUtils.getStaticFieldValue(Style.class, "STYLE_OPACITY");
		PropertyContainerHelper.setProperty(style, opacityField, String.valueOf(value));
	}

	@PatchMethod
	public static void cssClearOpacity(Object domImpl, Style style) {
		String opacityField = GwtTestReflectionUtils.getStaticFieldValue(Style.class, "STYLE_OPACITY");
		PropertyContainerHelper.setProperty(style, opacityField, "");
	}

	private static OverrideNodeList<Node> getChildNodeList(Object domImpl, Node node) {
		return PropertyContainerHelper.getProperty(node, NODE_LIST_FIELD);
	}

	public static InputElement createInputElement(Document doc, String type, String name) {
		InputElement e = (InputElement) doc.createElement("input");
		PropertyContainerHelper.setProperty(e, "Type", type);

		if (name != null) {
			PropertyContainerHelper.setProperty(e, "Name", name);
		}

		return e;
	}

	@PatchMethod
	public static void scrollIntoView(Object domImpl, Element elem) {

	}

	// Abstract methods

	@PatchMethod
	public static boolean isOrHasChild(Object domImpl, Node parent, Node child) {
		if (parent.equals(child)) {
			return true;
		} else if (child.getParentElement() != null && child.getParentElement().equals(parent)) {
			return true;
		}
		return false;
	}

	@PatchMethod
	public static InputElement createInputRadioElement(Object domImpl, Document doc, String name) {
		return DOMImplPatcher.createInputElement(doc, "RADIO", name);
	}

	@PatchMethod
	public static EventTarget eventGetTarget(Object domImpl, NativeEvent nativeEvent) {
		return null;
	}

	@PatchMethod
	public static void eventPreventDefault(Object domImpl, NativeEvent evt) {

	}

	@PatchMethod
	public static NativeEvent createHtmlEvent(Object domImpl, Document doc, String type, boolean canBubble, boolean cancelable) {
		return EventBuilder.create(Event.getTypeInt(type)).build();
	}

}
