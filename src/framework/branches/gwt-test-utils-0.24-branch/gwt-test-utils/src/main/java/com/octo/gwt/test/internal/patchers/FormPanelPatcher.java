package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.OverrideSubmitCompleteEvent;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(FormPanel.class)
public class FormPanelPatcher {

	@PatchMethod
	public static void submit(FormPanel formPanel) {
		FormPanel.SubmitEvent event = new FormPanel.SubmitEvent();
		formPanel.fireEvent(event);
		if (!event.isCanceled()) {
			FormPanelImpl impl = GwtReflectionUtils.getPrivateFieldValue(formPanel, "impl");
			Element synthesizedFrame = GwtReflectionUtils.getPrivateFieldValue(formPanel, "synthesizedFrame");
			FormPanel.SubmitCompleteEvent completeEvent = createCompleteSubmitEvent(impl.getContents(synthesizedFrame));
			formPanel.fireEvent(completeEvent);
		}

	}

	private static SubmitCompleteEvent createCompleteSubmitEvent(String resultsHtml) {
		return new OverrideSubmitCompleteEvent(resultsHtml);
	}

}
