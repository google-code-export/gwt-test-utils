package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.ui.TextArea;
import com.octo.gwt.test.internal.utils.ElementUtils;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(TextArea.class)
public class TextAreaPatcher extends AutomaticPatcher {

	@PatchMethod
	public static TextAreaElement getTextAreaElement(TextArea textArea) {
		return ElementUtils.castToDomElement(textArea.getElement());
	}

}
