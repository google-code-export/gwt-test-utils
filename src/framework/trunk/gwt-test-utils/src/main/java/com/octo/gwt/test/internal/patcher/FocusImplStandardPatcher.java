package com.octo.gwt.test.internal.patcher;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class FocusImplStandardPatcher extends AutomaticPatcher {

	@PatchMethod
	public static JavaScriptObject createFocusHandler(FocusImpl focusImpl) {
		return null;
	}

	@PatchMethod
	public static Element createFocusable(FocusImpl focusImpl) {
		return FocusImplPatcher.createFocusable(focusImpl);
	}

}
