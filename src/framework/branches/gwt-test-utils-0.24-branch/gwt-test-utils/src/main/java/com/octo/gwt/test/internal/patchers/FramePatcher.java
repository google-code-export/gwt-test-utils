package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.ui.Frame;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Frame.class)
public class FramePatcher {

	@PatchMethod
	public static String getUrl(Frame frame) {
		IFrameElement e = frame.getElement().cast();
		return e.getSrc();
	}

	@PatchMethod
	public static void setUrl(Frame frame, String url) {
		IFrameElement e = frame.getElement().cast();
		e.setSrc(url);
	}

}
