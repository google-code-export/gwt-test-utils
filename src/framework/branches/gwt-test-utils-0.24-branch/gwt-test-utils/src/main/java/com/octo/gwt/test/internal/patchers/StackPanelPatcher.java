package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(StackPanel.class)
public class StackPanelPatcher {

	@PatchMethod
	public static int findDividerIndex(StackPanel panel, com.google.gwt.user.client.Element child) {
		WidgetCollection children = GwtReflectionUtils.getPrivateFieldValue(panel, "children");

		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getElement().equals(child)) {
				return i;
			}
		}

		return -1;
	}

}
