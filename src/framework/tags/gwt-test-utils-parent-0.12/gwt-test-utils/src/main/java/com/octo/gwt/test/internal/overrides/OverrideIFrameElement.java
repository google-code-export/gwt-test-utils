package com.octo.gwt.test.internal.overrides;

import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.dom.client.IFrameElement;
import com.octo.gwt.test.internal.dom.UserElement;

public class OverrideIFrameElement extends IFrameElement {

	private OverrideFrameElement frameElement;

	public OverrideIFrameElement(UserElement element) {
		frameElement = new OverrideFrameElement(element);
	}

	public FrameElement overrideCast() {
		return frameElement;
	}
}
