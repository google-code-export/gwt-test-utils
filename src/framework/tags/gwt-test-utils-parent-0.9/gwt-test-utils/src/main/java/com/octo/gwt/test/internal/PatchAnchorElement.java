package com.octo.gwt.test.internal;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Element;
import com.octo.gwt.test.internal.dom.UserElement;
import com.octo.gwt.test.internal.overrides.OverrideAnchorElement;

public class PatchAnchorElement extends AnchorElement {

	public static AnchorElement as(Element elem) {
		return new OverrideAnchorElement((UserElement) elem);
	}

}
