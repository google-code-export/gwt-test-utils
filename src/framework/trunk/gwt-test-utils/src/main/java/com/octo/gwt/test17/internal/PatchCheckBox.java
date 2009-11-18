package com.octo.gwt.test17.internal;

import com.google.gwt.user.client.ui.CheckBox;
import com.octo.gwt.test17.internal.dom.UserElement;

public class PatchCheckBox {

	public static void setName(CheckBox box, String name) {
		UserElement element = (UserElement) box.getElement();
		element.setOverrideAttribute("name", name);
	}

	public static String getName(CheckBox box) {
		UserElement element = (UserElement) box.getElement();
		return element.getOverrideAttribute("name");
	}

	public static void setText(CheckBox box, String text) {
		UserElement element = (UserElement) box.getElement();
		element.setOverrideAttribute("text", text);
	}

	public static String getText(CheckBox box) {
		UserElement element = (UserElement) box.getElement();
		return element.getOverrideAttribute("text");
	}

	public static void setHTML(CheckBox box, String html) {
		UserElement element = (UserElement) box.getElement();
		element.setOverrideAttribute("html", html);
	}

	public static String getHTML(CheckBox box) {
		UserElement element = (UserElement) box.getElement();
		return element.getOverrideAttribute("html");
	}

}
