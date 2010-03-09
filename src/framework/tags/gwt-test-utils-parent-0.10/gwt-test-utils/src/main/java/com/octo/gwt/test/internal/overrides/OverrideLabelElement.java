package com.octo.gwt.test.internal.overrides;

import com.google.gwt.dom.client.LabelElement;
import com.octo.gwt.test.internal.dom.UserElement;

public class OverrideLabelElement extends LabelElement implements UserElementWrapper {

	private UserElement wrappedElement;

	public OverrideLabelElement(UserElement element) {
		wrappedElement = element;
	}

	public UserElement getWrappedElement() {
		return wrappedElement;
	}

	public String getOverrideHtmlFor() {
		return this.wrappedElement.getOverrideAttribute("htmlFor");
	}

	public void setOverrideHtmlFor(String overrideHtmlFor) {
		this.wrappedElement.setOverrideAttribute("htmlFor", overrideHtmlFor);
	}

	public static OverrideLabelElement overrideCast(Object o) {
		if (o instanceof OverrideLabelElement) {
			OverrideLabelElement e = (OverrideLabelElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
