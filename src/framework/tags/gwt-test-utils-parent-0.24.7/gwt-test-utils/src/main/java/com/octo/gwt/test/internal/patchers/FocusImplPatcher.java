package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(FocusImpl.class)
public class FocusImplPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void blur(FocusImpl focusImpl, Element element) {

	}

	@PatchMethod
	public static void focus(FocusImpl focusImpl, Element element) {

	}

	@PatchMethod
	public static int getTabIndex(FocusImpl focusImpl, Element elem) {
		return PropertyContainerHelper.getPropertyInteger(elem, "TabIndex");
	}

	@PatchMethod
	public static void setTabIndex(FocusImpl focusImpl, Element elem, int index) {
		PropertyContainerHelper.setProperty(elem, "TabIndex", index);
	}

}
