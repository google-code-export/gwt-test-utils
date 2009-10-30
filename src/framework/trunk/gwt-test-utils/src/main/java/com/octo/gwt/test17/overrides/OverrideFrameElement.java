package com.octo.gwt.test17.overrides;

import com.google.gwt.dom.client.FrameElement;

public class OverrideFrameElement extends FrameElement {

	private String overrideSrc;

	public String getOverrideSrc() {
		return overrideSrc;
	}

	public void setOverrideSrc(String overrideSrc) {
		this.overrideSrc = overrideSrc;
	}

	public static OverrideFrameElement overrideCast(Object o) {
		if (o instanceof OverrideFrameElement) {
			OverrideFrameElement e = (OverrideFrameElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
