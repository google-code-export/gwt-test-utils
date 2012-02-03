package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(RootPanel.class)
public class RootPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static com.google.gwt.user.client.Element getBodyElement() {
		return Document.get().getBody().cast();
	}

	@PatchMethod
	public static boolean isElementChildOfWidget(Element element) {
		// Walk up the DOM hierarchy, looking for any widget with an event
		// listener
		// set. Though it is not dependable in the general case that a widget
		// will
		// have set its element's event listener at all times, it *is*
		// dependable
		// if the widget is attached. Which it will be in this case.
		element = element.getParentElement();
		BodyElement body = Document.get().getBody();
		while (element != null && body != element) {
			if (Event.getEventListener(element) != null) {
				return true;
			}
			element = element.getParentElement();
		}
		return false;
	}

}
