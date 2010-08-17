package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.impl.DOMImpl;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.utils.ElementUtils;
import com.octo.gwt.test.utils.events.EventUtils;

@PatchClass(DOMImpl.class)
public class DOMImplUserPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void setEventListener(DOMImpl domImpl, Element elem, EventListener listener) {

	}

	@PatchMethod
	public static int getEventsSunk(DOMImpl domImpl, Element elem) {
		return 1;
	}

	@PatchMethod
	public static int eventGetTypeInt(DOMImpl domImpl, String type) {
		return EventUtils.getEventTypeInt(type);
	}

	// Abstract methods

	@PatchMethod
	public static void insertChild(DOMImpl domImpl, Element userParent, Element userChild, int index) {
		NodePatcher.insertAtIndex(userParent, userChild, index);
	}

	@PatchMethod
	public static Element getChild(DOMImpl domImpl, Element userElem, int index) {
		com.google.gwt.dom.client.Element elem = ElementUtils.castToDomElement(userElem);
		if (index >= elem.getChildNodes().getLength()) {
			return null;
		}

		return ElementUtils.castToUserElement((com.google.gwt.dom.client.Element) elem.getChildNodes().getItem(index));
	}

	@PatchMethod
	public static int getChildIndex(DOMImpl domImpl, Element parent, Element child) {
		if (parent == null || child == null) {
			return -1;
		}

		parent = ElementUtils.castToUserElement(parent);
		com.google.gwt.dom.client.Element domElem = ElementUtils.castToDomElement(child);

		for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
			if (domElem.equals(parent.getChildNodes().getItem(i))) {
				return i;
			}
		}

		return -1;
	}

	@PatchMethod
	public static void sinkEvents(DOMImpl domImpl, Element elem, int eventBits) {

	}

	@PatchMethod
	public static void initEventSystem(DOMImpl domImpl) {
	}

	@PatchMethod
	public static int getChildCount(Object domImpl, Element elem) {
		return elem.getChildCount();
	}

}
