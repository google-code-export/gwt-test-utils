package com.octo.gwt.test.internal.patcher;

import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(Widget.class)
public class WidgetPatcher extends AutomaticPatcher {

	// TODO : remove this when cast() will be patched
	@PatchMethod
	public static void onBrowserEvent(Widget widget, Event event) {
		DomEvent.fireNativeEvent(event, widget, widget.getElement());
	}

}
