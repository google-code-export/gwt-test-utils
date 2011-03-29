package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test.internal.overrides.OverrideEvent;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Event.class)
public class EventPatcher extends AutomaticPatcher {

	@PatchMethod
	public static final Element getTarget(Event event) {
		OverrideEvent overrideEvent = event.cast();
		return overrideEvent.getOverrideTargetElement();
	}

	@PatchMethod
	public static final Element getRelatedTarget(Event event) {
		return getTarget(event);
	}

}
