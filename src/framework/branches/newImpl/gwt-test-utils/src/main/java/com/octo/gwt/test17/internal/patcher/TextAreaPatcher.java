package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.ui.TextArea;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class TextAreaPatcher extends AutomaticPatcher {

	@PatchMethod
	public static TextAreaElement getTextAreaElement(TextArea textArea) {
		return ElementUtils.castToDomElement(textArea.getElement());
	}

}
