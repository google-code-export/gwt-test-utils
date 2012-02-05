package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.events.OverrideEvent;

@PatchClass(Event.class)
public class EventPatcher {

	@PatchMethod
	public static final Element getTarget(Event event) {
		return ((OverrideEvent) event).getOverrideTargetElement();
	}

	@PatchMethod
	public static final Element getRelatedTarget(Event event) {
		return getTarget(event);
	}

}
