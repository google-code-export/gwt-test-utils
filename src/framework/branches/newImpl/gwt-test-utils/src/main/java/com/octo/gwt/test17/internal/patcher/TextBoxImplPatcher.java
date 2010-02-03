package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test17.internal.patcher.tools.SubClassedHelper;

public class TextBoxImplPatcher extends AutomaticPatcher {

	private static final String SELECTION_START = "SelectionStart";
	private static final String SELECTION_END = "SelectionEnd";

	@PatchMethod
	public static void setSelectionRange(TextBoxImpl textBoxImpl, Element e, int pos, int length) {
		SubClassedHelper.setProperty(e, SELECTION_START, pos);
		SubClassedHelper.setProperty(e, SELECTION_END, pos + length);
	}

	@PatchMethod
	public static int getCursorPos(TextBoxImpl textBoxImpl, Element e) {
		return SubClassedHelper.getPropertyInteger(e, SELECTION_START);
	}

	@PatchMethod
	public static int getSelectionLength(TextBoxImpl textBoxImpl, Element e) {
		int selectionStart = SubClassedHelper.getPropertyInteger(e, SELECTION_START);
		int selectionEnd = SubClassedHelper.getPropertyInteger(e, SELECTION_END);
		return selectionEnd - selectionStart;
	}

}
