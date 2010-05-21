package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.impl.DOMImpl;
import com.octo.gwt.test.EventUtils;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class DOMImplUserPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void setEventListener(DOMImpl domImpl, Element elem, EventListener listener) {

	}

	@PatchMethod
	public static int getEventsSunk(DOMImpl domImpl, Element elem) {
		return 1;
	}

	@PatchMethod(args = { String.class })
	public static int eventGetTypeInt(DOMImpl domImpl, String type) {
		return EventUtils.getEventTypeInt(type);
	}

}
