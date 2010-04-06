package com.octo.gwt.test.internal;

import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.ui.TextArea;
import com.octo.gwt.test.internal.dom.UserElement;
import com.octo.gwt.test.internal.overrides.OverrideTextAreaElement;

public class PatchTextArea {

	public static TextAreaElement getTextAreaElement(TextArea textArea) {
		return new OverrideTextAreaElement((UserElement) textArea.getElement());
	}

}
