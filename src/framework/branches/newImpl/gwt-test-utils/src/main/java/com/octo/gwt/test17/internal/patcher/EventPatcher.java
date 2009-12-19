package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.overrides.OverrideEvent;

public class EventPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getTarget")) {
			return callMethod("getTarget", "this");
		} else if (match(m, "getRelatedTarget")) {
			return callMethod("getTarget", "this");
		} else if (match(m, "getEventsSunk")) {
			return callMethod("getEventsSunk", "$1");
		} else if (match(m, "sinkEvents")) {
			return callMethod("sinkEvents", "$1, $2");
		}

		return null;
	}

	public static final Element getTarget(Event event) {
		return ((OverrideEvent) event).getOverrideTargetElement();
	}

	public static int getEventsSunk(Element elem) {
		return DOM.getEventsSunk(ElementUtils.castToUserElement(elem));
	}

	public static void sinkEvents(Element elem, int eventBits) {
		DOM.sinkEvents(ElementUtils.castToUserElement(elem), eventBits);
	}
}
