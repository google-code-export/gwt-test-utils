package com.octo.gwt.test17.internal.overrides;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test17.internal.patcher.PropertyHolder;

public class OverrideInputElement extends InputElement {

	public OverrideInputElement(String type) {
		this(null, type);
	}

	public OverrideInputElement(String name, String type) {
		if (name != null)
			PropertyHolder.get(this).put("Name", name);
		PropertyHolder.get(this).put("Type", type);
	}

}
