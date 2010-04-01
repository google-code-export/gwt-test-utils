package com.octo.gwt.test.internal;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.octo.gwt.test.internal.overrides.OverrideWidgetCollection;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class PatchComplexPanel {

	public static WidgetCollection getChildren(ComplexPanel panel) {

		WidgetCollection col = GwtTestReflectionUtils.getPrivateFieldValue(panel, "children");

		if (!(col instanceof OverrideWidgetCollection)) {
			col = new OverrideWidgetCollection(panel);
			GwtTestReflectionUtils.setPrivateFieldValue(panel, "children", col);
		}

		return col;
	}

}
