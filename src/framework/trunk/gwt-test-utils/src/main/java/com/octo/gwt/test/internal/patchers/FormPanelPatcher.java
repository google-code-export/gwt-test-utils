package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.OverrideSubmitCompleteEvent;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(FormPanel.class)
public class FormPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void submit(FormPanel formPanel) {
		FormPanel.SubmitEvent event = new FormPanel.SubmitEvent();
		formPanel.fireEvent(event);
		if (!event.isCanceled()) {
			FormPanelImpl impl = GwtTestReflectionUtils.getPrivateFieldValue(formPanel, "impl");
			Element synthesizedFrame = GwtTestReflectionUtils.getPrivateFieldValue(formPanel, "synthesizedFrame");
			FormPanel.SubmitCompleteEvent completeEvent = createCompleteSubmitEvent(impl.getContents(synthesizedFrame));
			formPanel.fireEvent(completeEvent);
		}

	}

	private static SubmitCompleteEvent createCompleteSubmitEvent(String resultsHtml) {
		return new OverrideSubmitCompleteEvent(resultsHtml);
	}

}
