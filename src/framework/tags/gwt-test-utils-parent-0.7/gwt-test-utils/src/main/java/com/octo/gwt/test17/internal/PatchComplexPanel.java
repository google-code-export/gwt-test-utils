package com.octo.gwt.test17.internal;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.internal.overrides.OverrideWidgetCollection;

public class PatchComplexPanel {

	public static WidgetCollection getChildren(ComplexPanel panel) {

		WidgetCollection col = ReflectionUtils.getPrivateFieldValue(panel, "children");

		if (!(col instanceof OverrideWidgetCollection)) {
			col = new OverrideWidgetCollection(panel);
			ReflectionUtils.setPrivateField(panel, "children", col);
		}

		return col;
	}

}
