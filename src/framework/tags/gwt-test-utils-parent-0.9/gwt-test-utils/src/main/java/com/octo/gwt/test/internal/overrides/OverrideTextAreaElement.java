package com.octo.gwt.test.internal.overrides;

import com.google.gwt.dom.client.TextAreaElement;
import com.octo.gwt.test.internal.dom.UserElement;

public class OverrideTextAreaElement extends TextAreaElement implements UserElementWrapper {

	private UserElement wrappedElement;

	public OverrideTextAreaElement(UserElement element) {
		wrappedElement = element;
	}

	public int getOverrideRows() {
		return Integer.valueOf(this.wrappedElement.getOverrideAttribute("rows"));
	}

	public void setOverrideRows(int overrideRows) {
		this.wrappedElement.setOverrideAttribute("rows", String.valueOf(overrideRows));
	}

	public UserElement getWrappedElement() {
		return wrappedElement;
	}

	public static OverrideTextAreaElement overrideCast(Object o) {
		if (o instanceof OverrideTextAreaElement) {
			OverrideTextAreaElement e = (OverrideTextAreaElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
