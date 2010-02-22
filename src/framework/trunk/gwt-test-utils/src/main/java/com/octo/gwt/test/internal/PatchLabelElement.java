package com.octo.gwt.test.internal;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LabelElement;
import com.octo.gwt.test.internal.dom.UserElement;
import com.octo.gwt.test.internal.overrides.OverrideLabelElement;

public class PatchLabelElement {

	public static LabelElement as(Element elem) {
		return new OverrideLabelElement((UserElement) elem);
	}

}
