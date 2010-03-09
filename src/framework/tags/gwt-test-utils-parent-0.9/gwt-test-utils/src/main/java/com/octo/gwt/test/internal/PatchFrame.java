package com.octo.gwt.test.internal;

import com.google.gwt.user.client.ui.Frame;
import com.octo.gwt.test.internal.dom.UserElement;
import com.octo.gwt.test.internal.overrides.OverrideFrameElement;

public class PatchFrame {

	public static OverrideFrameElement getFrameElement(Frame frame) {
		return new OverrideFrameElement((UserElement) frame.getElement());
	}

}
