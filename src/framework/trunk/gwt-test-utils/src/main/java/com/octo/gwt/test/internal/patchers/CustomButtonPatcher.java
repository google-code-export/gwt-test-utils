package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CustomButton;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.events.EventBuilder;

@PatchClass(CustomButton.class)
public class CustomButtonPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void onClick(CustomButton button) {

		// Allow the click we're about to synthesize to pass through to the
		// superclass and containing elements. Element.dispatchEvent() is
		// synchronous, so we simply set and clear the flag within this method.
		GwtTestReflectionUtils.setPrivateFieldValue(button, "allowClick", true);

		Event clickEvent = EventBuilder.create(Event.ONCLICK).build();
		button.onBrowserEvent(clickEvent);

		GwtTestReflectionUtils.setPrivateFieldValue(button, "allowClick", false);
	}

}
