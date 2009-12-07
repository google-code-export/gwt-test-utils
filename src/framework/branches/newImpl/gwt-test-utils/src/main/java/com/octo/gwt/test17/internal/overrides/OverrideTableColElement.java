package com.octo.gwt.test17.internal.overrides;

import com.google.gwt.dom.client.TableColElement;

public class OverrideTableColElement extends TableColElement implements TagAware {

	private String tag;

	public OverrideTableColElement(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
}
