package com.octo.gwt.test17.overrides;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;

public class OverrideInputElement extends InputElement {

	@SuppressWarnings("unused")
	private Document document;
	
	@SuppressWarnings("unused")
	private String type;

	private String overrideName;
	private int overrideTabIndex;
	private boolean overrideDefaultChecked = false;
	private boolean overrideChecked = overrideDefaultChecked;
	
	public OverrideInputElement(String type) {
		this.type = type;
	}

	public OverrideInputElement(Document document) {
		this.document = document;
	}

	public String getOverrideName() {
		return overrideName;
	}

	public void setOverrideName(String overrideName) {
		this.overrideName = overrideName;
	}

	public int getOverrideTabIndex() {
		return overrideTabIndex;
	}

	public void setOverrideTabIndex(int overrideTabIndex) {
		this.overrideTabIndex = overrideTabIndex;
	}

	public boolean isOverrideDefaultChecked() {
		return overrideDefaultChecked;
	}

	public void setOverrideDefaultChecked(boolean overrideDefaultChecked) {
		this.overrideDefaultChecked = overrideDefaultChecked;
	}

	public boolean isOverrideChecked() {
		return overrideChecked;
	}

	public void setOverrideChecked(boolean overrideChecked) {
		this.overrideChecked = overrideChecked;
	}

	public static OverrideInputElement overrideCast(Object o) {
		if (o instanceof OverrideInputElement) {
			OverrideInputElement e = (OverrideInputElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
