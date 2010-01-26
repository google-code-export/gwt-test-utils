package com.octo.gwt.test17.internal.patcher.dom;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.impl.DOMImpl;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class DOMImplUserPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void setEventListener(DOMImpl domImpl, Element elem, EventListener listener) {
		
	}
	
	@PatchMethod
	public static int getEventsSunk(DOMImpl domImpl, Element elem) {
		return 1;
	}

	@PatchMethod(args={String.class})
	public static int eventGetTypeInt(DOMImpl domImpl, String type) {
		if (type.equals("blur")) {
			return Event.ONBLUR;
		} else if (type.equals("change")) {
			return Event.ONCHANGE;
		} else if (type.equals("click")) {
			return Event.ONCLICK;
		} else if (type.equals("dblclick")) {
			return Event.ONDBLCLICK;
		} else if (type.equals("focus")) {
			return Event.ONFOCUS;
		} else if (type.equals("keydown")) {
			return Event.ONKEYDOWN;
		} else if (type.equals("keypress")) {
			return Event.ONKEYPRESS;
		} else if (type.equals("keyup")) {
			return Event.ONKEYUP;
		} else if (type.equals("load")) {
			return Event.ONLOAD;
		} else if (type.equals("losecapture")) {
			return Event.ONLOSECAPTURE;
		} else if (type.equals("mousedown")) {
			return Event.ONMOUSEDOWN;
		} else if (type.equals("mousemove")) {
			return Event.ONMOUSEMOVE;
		} else if (type.equals("mouseout")) {
			return Event.ONMOUSEOUT;
		} else if (type.equals("mouseover")) {
			return Event.ONMOUSEOVER;
		} else if (type.equals("mouseup")) {
			return Event.ONMOUSEUP;
		} else if (type.equals("scroll")) {
			return Event.ONSCROLL;
		} else if (type.equals("error")) {
			return Event.ONERROR;
		} else if (type.equals("mousewheel")) {
			return Event.ONMOUSEWHEEL;
		} else if (type.equals("contextmenu")) {
			return Event.ONCONTEXTMENU;
		}

		throw new RuntimeException("Unable to convert DOM Event \"" + type + "\" to an integer");
	}
	
}
