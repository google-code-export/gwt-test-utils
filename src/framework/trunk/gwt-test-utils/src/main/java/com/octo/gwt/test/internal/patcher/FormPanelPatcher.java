package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.OverrideSubmitCompleteEvent;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(FormPanel.class)
public class FormPanelPatcher extends AutomaticPatcher {

	//FIXME : implements correctly the element.setInnerHTML method..
	@PatchMethod
	public static void createFrame(FormPanel formPanel) {
		IFrameElement element = Document.get().createIFrameElement();

		GwtTestReflectionUtils.setPrivateFieldValue(formPanel, "synthesizedFrame", element);
	}

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
