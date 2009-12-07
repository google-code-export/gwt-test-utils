package com.octo.gwt.test17.internal.overrides;

import com.google.gwt.dom.client.HeadingElement;

public class OverrideHeadingElement extends HeadingElement implements TagAware {

	private String tag;

	public OverrideHeadingElement(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
}
