package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.internal.overrides.OverrideAnchorElement;

public class PatchAnchorElement {
	
	public static AnchorElement as(Element elem) {
		return new OverrideAnchorElement((UserElement) elem);
	}

}
