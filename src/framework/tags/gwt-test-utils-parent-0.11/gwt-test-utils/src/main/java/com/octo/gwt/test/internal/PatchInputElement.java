package com.octo.gwt.test.internal;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test.internal.dom.UserElement;
import com.octo.gwt.test.internal.overrides.OverrideInputElement;

public class PatchInputElement {

	public static InputElement as(Element elem) {
		return new OverrideInputElement((UserElement) elem);
	}

}
