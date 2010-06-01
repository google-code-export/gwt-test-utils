package com.octo.gwt.test.internal.patcher;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.FocusImplStandard;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class FocusImplStandardPatcher extends AutomaticPatcher {

	@PatchMethod
	public static JavaScriptObject createFocusHandler(FocusImplStandard focusImpl) {
		return null;
	}

	@PatchMethod
	public static Element createFocusable(FocusImplStandard focusImpl) {
		return FocusImplPatcher.createFocusable(focusImpl);
	}

}
