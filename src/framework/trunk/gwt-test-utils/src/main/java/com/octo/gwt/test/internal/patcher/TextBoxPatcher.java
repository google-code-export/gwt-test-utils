package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.utils.ElementUtils;

@PatchClass(TextBox.class)
public class TextBoxPatcher extends AutomaticPatcher {

	@PatchMethod
	public static InputElement getInputElement(TextBox textBox) {
		return ElementUtils.castToDomElement(textBox.getElement());
	}

}
