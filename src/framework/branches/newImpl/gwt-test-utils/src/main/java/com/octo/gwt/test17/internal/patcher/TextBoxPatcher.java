package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class TextBoxPatcher extends AutomaticPatcher {

	@PatchMethod
	public static InputElement getInputElement(TextBox textBox) {
		return ElementUtils.castToDomElement(textBox.getElement());
	}

}
