package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.ui.Frame;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

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
