package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test17.internal.patcher.tools.SubClassedHelper;

public class FocusImplOldPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void blur(FocusImpl focusImpl, Element element) {

	}

	@PatchMethod
	public static void focus(FocusImpl focusImpl, Element element) {

	}

	@PatchMethod
	public static JavaScriptObject createBlurHandler(FocusImpl focusImpl) {
		return null;
	}

	@PatchMethod
	public static JavaScriptObject createFocusHandler(FocusImpl focusImpl) {
		return null;
	}

	@PatchMethod
	public static JavaScriptObject createMouseHandler(FocusImpl focusImpl) {
		return null;
	}

	@PatchMethod
	public static Element createFocusable(FocusImpl focusImpl) {
		return FocusImplPatcher.createFocusable(focusImpl);
	}

	@PatchMethod
	public static int getTabIndex(FocusImpl focusImpl, Element elem) {
		return SubClassedHelper.getPropertyInteger(elem, "TabIndex");
	}

	@PatchMethod
	public static void setTabIndex(FocusImpl focusImpl, Element elem, int index) {
		SubClassedHelper.setProperty(elem, "TabIndex", index);
	}

}
