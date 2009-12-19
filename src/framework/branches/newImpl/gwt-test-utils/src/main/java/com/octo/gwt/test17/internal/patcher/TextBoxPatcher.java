package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test17.ElementUtils;

public class TextBoxPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getInputElement")) {
			return callMethod("getInputElement", "this");
		}

		return null;
	}

	public static InputElement getInputElement(TextBox textBox) {
		return ElementUtils.castToDomElement(textBox.getElement());
	}

}
