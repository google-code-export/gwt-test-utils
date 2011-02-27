package com.octo.gwt.test.internal.patchers;

import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.user.client.ui.LayoutCommand;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(LayoutPanel.class)
public class LayoutPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void animate(LayoutPanel panel, final int duration, final AnimationCallback callback) {
		LayoutCommand layoutCmd = GwtTestReflectionUtils.getPrivateFieldValue(panel, "layoutCmd");
		layoutCmd.schedule(0, callback);
	}

}
