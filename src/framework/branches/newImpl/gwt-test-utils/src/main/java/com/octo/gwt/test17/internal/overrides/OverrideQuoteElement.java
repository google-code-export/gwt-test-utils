package com.octo.gwt.test17.internal.overrides;

import com.google.gwt.dom.client.QuoteElement;

public class OverrideQuoteElement extends QuoteElement implements TagAware {

	private String tag;

	public OverrideQuoteElement(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

}
