package com.octo.gwt.test;

import com.google.gwt.user.client.Element;
import com.octo.gwt.test.internal.overrides.TagAware;

public class ElementWrapper extends Element implements TagAware {

	private com.google.gwt.dom.client.Element wrappedElement;

	public ElementWrapper(com.google.gwt.dom.client.Element wrappedElement) {
		this.wrappedElement = wrappedElement;
	}

	public com.google.gwt.dom.client.Element getWrappedElement() {
		return wrappedElement;
	}

	public String getTag() {
		return wrappedElement.getTagName();
	}
}
