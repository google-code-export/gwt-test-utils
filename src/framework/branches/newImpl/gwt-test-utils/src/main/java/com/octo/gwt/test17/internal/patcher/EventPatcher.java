package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.overrides.OverrideEvent;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class EventPatcher extends AutomaticPatcher {

	@PatchMethod
	public static final Element getTarget(Event event) {
		return ((OverrideEvent) event).getOverrideTargetElement();
	}
	
	@PatchMethod
	public static final Element getRelatedTarget(Event event) {
		return getTarget(event);
	}

	@PatchMethod
	public static int getEventsSunk(Element elem) {
		return DOM.getEventsSunk(ElementUtils.castToUserElement(elem));
	}

	@PatchMethod
	public static void sinkEvents(Element elem, int eventBits) {
		DOM.sinkEvents(ElementUtils.castToUserElement(elem), eventBits);
	}
	
}
