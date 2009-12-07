package com.octo.gwt.test17.internal.overrides;

import com.google.gwt.dom.client.TableSectionElement;

public class OverrideTableSectionElement extends TableSectionElement implements TagAware {

	private String tag;

	public OverrideTableSectionElement(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
