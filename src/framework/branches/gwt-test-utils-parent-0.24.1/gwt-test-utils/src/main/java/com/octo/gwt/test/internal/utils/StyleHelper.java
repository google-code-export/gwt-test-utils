package com.octo.gwt.test.internal.utils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.patchers.dom.StylePatcher;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class StyleHelper {

	public static Element getTargetElement(Style style) {
		return GwtReflectionUtils.getPrivateFieldValue(style, StylePatcher.TARGET_ELEMENT);
	}

}
