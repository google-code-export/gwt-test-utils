package com.octo.gwt.test17.internal;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.octo.gwt.test17.internal.dom.UserElement;

public class PatchTextBoxImpl {

	public static int getCursorPos(TextBoxImpl textBox, Element e) {
		UserElement userElement = (UserElement) e;
		return userElement.getPropertyInt("cursorPos");
	}

	public static void setSelectionRange(TextBoxImpl textBox, Element e, int pos) {
		UserElement userElement = (UserElement) e;
		userElement.setPropertyInt("cursorPos", pos);
	}
}
