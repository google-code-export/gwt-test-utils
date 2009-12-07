package com.octo.gwt.test17.internal.overrides;

import com.google.gwt.dom.client.TableCellElement;

public class OverrideTableCellElement extends TableCellElement implements TagAware {

	private String tag;

	public OverrideTableCellElement(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

}
