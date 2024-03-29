package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.octo.gwt.test.ElementUtils;
import com.octo.gwt.test.EventUtils;
import com.octo.gwt.test.internal.overrides.OverrideEvent;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class DOMImplSubClassPatcher extends AutomaticPatcher {

	@PatchMethod
	public static boolean isOrHasChild(Object domImpl, Node parent, Node child) {
		parent = ElementUtils.castToDomElement(parent);
		child = ElementUtils.castToDomElement(child);
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
		OverrideEvent event = new OverrideEvent(EventUtils.getEventTypeInt(type));
		return event;
	}

}
