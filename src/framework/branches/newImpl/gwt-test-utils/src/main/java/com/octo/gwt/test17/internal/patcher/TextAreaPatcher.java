package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.ui.TextArea;
import com.octo.gwt.test17.ElementUtils;

public class TextAreaPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getTextAreaElement")) {
			return callMethod("getTextAreaElement", "this");
		}

		return null;
	}

	public static TextAreaElement getTextAreaElement(TextArea textArea) {
		return ElementUtils.castToDomElement(textArea.getElement());
	}

}
