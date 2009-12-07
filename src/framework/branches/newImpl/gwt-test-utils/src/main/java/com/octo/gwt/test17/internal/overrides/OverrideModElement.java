package com.octo.gwt.test17.internal.overrides;

import com.google.gwt.dom.client.ModElement;

public class OverrideModElement extends ModElement implements TagAware {

	private String tag;

	public OverrideModElement(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
}
