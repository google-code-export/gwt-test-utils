package com.octo.gwt.test17.overrides;

import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.dom.client.IFrameElement;

public class OverrideIFrameElement extends IFrameElement {

	private OverrideFrameElement frameElement;

	public OverrideIFrameElement() {
		frameElement = new OverrideFrameElement();
	}

	public FrameElement overrideCast() {
		return frameElement;
	}
}
