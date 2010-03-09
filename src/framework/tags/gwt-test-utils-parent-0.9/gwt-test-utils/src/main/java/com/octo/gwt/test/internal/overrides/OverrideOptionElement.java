package com.octo.gwt.test.internal.overrides;

import com.google.gwt.dom.client.OptionElement;

public class OverrideOptionElement extends OptionElement {

	private boolean overrideSelected;

	private String overrideText;

	private String overrideValue;

	public String getOverrideText() {
		return overrideText;
	}

	public void setOverrideText(String overrideText) {
		this.overrideText = overrideText;
	}

	public String getOverrideValue() {
		return overrideValue;
	}

	public void setOverrideValue(String overrideValue) {
		this.overrideValue = overrideValue;
	}

	public boolean isOverrideSelected() {
		return overrideSelected;
	}

	public void setOverrideSelected(boolean overrideSelected) {
		this.overrideSelected = overrideSelected;
	}

	public static OverrideOptionElement overrideCast(Object o) {
		if (o instanceof OverrideOptionElement) {
			OverrideOptionElement e = (OverrideOptionElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
