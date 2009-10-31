package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.internal.overrides.OverrideInputElement;

public class PatchInputElement {

	public static InputElement as(Element elem) {
		return new OverrideInputElement((UserElement) elem);
	}

}
