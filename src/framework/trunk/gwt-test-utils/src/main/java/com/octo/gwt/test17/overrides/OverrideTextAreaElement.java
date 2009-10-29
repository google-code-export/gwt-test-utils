package com.octo.gwt.test17.overrides;

import com.google.gwt.dom.client.TextAreaElement;
import com.octo.gwt.test17.internal.dom.UserElement;

public class OverrideTextAreaElement extends TextAreaElement {

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
	
	public static OverrideTextAreaElement overrideCast(Object o) {
		if (o instanceof OverrideTextAreaElement) {
			OverrideTextAreaElement e = (OverrideTextAreaElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
