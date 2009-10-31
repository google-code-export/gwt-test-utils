package com.octo.gwt.test17.overrides;

import com.google.gwt.dom.client.ImageElement;
import com.octo.gwt.test17.internal.dom.UserElement;

public class OverrideImageElement extends ImageElement implements UserElementWrapper {
	
	private UserElement wrappedElement;
	
	public OverrideImageElement(UserElement element) {
		wrappedElement = element;
	}
	
	public String getOverrideSrc() {
		return wrappedElement.getOverrideAttribute("src");
	}

	public void setOverrideSrc(String overrideSrc) {
		wrappedElement.setOverrideAttribute("src", overrideSrc);
	}

	public UserElement getWrappedElement() {
		return wrappedElement;
	}

	public static OverrideImageElement overrideCast(Object o) {
		if (o instanceof OverrideImageElement) {
			OverrideImageElement e = (OverrideImageElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
