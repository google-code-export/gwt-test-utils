package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.widget.ComponentHelper;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(ComponentHelper.class)
public class ComponentHelperPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void setParent(Widget parent, Widget child) {
		GwtTestReflectionUtils.callPrivateMethod(child, "setParent", parent);
	}

	@PatchMethod
	public static void doAttachNative(Widget widget) {
		GwtTestReflectionUtils.callPrivateMethod(widget, "onAttach");
	}

	@PatchMethod
	public static void doDetachNative(Widget widget) {
		GwtTestReflectionUtils.callPrivateMethod(widget, "onDetach");
	}

}
