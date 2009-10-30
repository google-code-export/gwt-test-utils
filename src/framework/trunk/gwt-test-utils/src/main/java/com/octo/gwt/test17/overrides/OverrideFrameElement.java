package com.octo.gwt.test17.overrides;

import com.google.gwt.dom.client.FrameElement;
import com.octo.gwt.test17.internal.dom.UserElement;

public class OverrideFrameElement extends FrameElement {

	private UserElement wrappedElement;

	public OverrideFrameElement(UserElement element) {
		wrappedElement = element;
	}

	public String getOverrideSrc() {
		return wrappedElement.getOverrideAttribute("src");
	}

	public void setOverrideSrc(String overrideSrc) {
		wrappedElement.setOverrideAttribute("src", overrideSrc);
	}

	public static OverrideFrameElement overrideCast(Object o) {
		if (o instanceof OverrideFrameElement) {
			OverrideFrameElement e = (OverrideFrameElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
