package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.internal.overrides.OverrideInputElement;

public class PatchTextBox {
	
	public static InputElement getInputElement(TextBox textBox) {
		return new OverrideInputElement((UserElement) textBox.getElement());
	}

}
