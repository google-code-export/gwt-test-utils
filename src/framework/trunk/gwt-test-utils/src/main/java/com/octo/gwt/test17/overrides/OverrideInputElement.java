package com.octo.gwt.test17.overrides;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;

public class OverrideInputElement extends InputElement {

	@SuppressWarnings("unused")
	private Document document;
	
	@SuppressWarnings("unused")
	private String type;

	private String overrideName;
	
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

	public static OverrideInputElement overrideCast(Object o) {
		if (o instanceof OverrideInputElement) {
			OverrideInputElement e = (OverrideInputElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
