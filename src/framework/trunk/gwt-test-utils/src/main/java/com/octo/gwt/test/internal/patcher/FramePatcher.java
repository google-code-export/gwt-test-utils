package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.ui.Frame;
import com.octo.gwt.test.internal.utils.ElementUtils;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(Frame.class)
public class FramePatcher extends AutomaticPatcher {

	@PatchMethod
	public static String getUrl(Frame frame) {
		return ((IFrameElement) ElementUtils.castToDomElement(frame.getElement())).getSrc();
	}

	@PatchMethod
	public static void setUrl(Frame frame, String url) {
		((IFrameElement) ElementUtils.castToDomElement(frame.getElement())).setSrc(url);
	}

}
