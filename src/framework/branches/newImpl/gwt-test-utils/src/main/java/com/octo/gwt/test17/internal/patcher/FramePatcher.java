package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.ui.Frame;
import com.octo.gwt.test17.ElementUtils;

public class FramePatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getUrl")) {
			return callMethod("getUrl", "this");
		} else if (match(m, "setUrl")) {
			return callMethod("setUrl", "this, $1");
		}

		return null;
	}

	public static String getUrl(Frame frame) {
		return ((IFrameElement) ElementUtils.castToDomElement(frame.getElement())).getSrc();
	}

	public static void setUrl(Frame frame, String url) {
		((IFrameElement) ElementUtils.castToDomElement(frame.getElement())).setSrc(url);
	}

}
