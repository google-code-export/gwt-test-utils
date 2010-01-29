package com.octo.gwt.test17.internal.patcher.dom;

import com.google.gwt.dom.client.Style;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticSubclasser;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test17.internal.patcher.tools.SubClassedHelper;

public class StylePatcher extends AutomaticSubclasser {

	public StylePatcher() {
		disableGetAndSetPatch = true;
	}
	
	@PatchMethod
	public static String getPropertyImpl(Style style, String propertyName) {
		return SubClassedHelper.getProperty(style, propertyName);
	}

	@PatchMethod
	public static void setPropertyImpl(Style style, String propertyName, String propertyValue) {
		SubClassedHelper.setProperty(style, propertyName, propertyValue);
	}
	
}
