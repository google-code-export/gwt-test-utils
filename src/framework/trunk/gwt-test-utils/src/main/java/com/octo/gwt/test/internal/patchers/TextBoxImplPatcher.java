package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(TextBoxImpl.class)
public class TextBoxImplPatcher extends AutomaticPatcher {

	private static final String SELECTION_START = "SelectionStart";
	private static final String SELECTION_END = "SelectionEnd";

	@PatchMethod
	public static void setSelectionRange(TextBoxImpl textBoxImpl, Element e, int pos, int length) {
		PropertyContainerUtils.setProperty(e, SELECTION_START, pos);
		PropertyContainerUtils.setProperty(e, SELECTION_END, pos + length);
	}

	@PatchMethod
	public static int getCursorPos(TextBoxImpl textBoxImpl, Element e) {
		return PropertyContainerUtils.getPropertyInteger(e, SELECTION_START);
	}

	@PatchMethod
	public static int getSelectionLength(TextBoxImpl textBoxImpl, Element e) {
		int selectionStart = PropertyContainerUtils.getPropertyInteger(e, SELECTION_START);
		int selectionEnd = PropertyContainerUtils.getPropertyInteger(e, SELECTION_END);
		return selectionEnd - selectionStart;
	}

}
