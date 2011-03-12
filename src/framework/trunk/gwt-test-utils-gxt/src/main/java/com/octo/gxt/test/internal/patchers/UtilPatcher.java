package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.util.Util;
import com.octo.gwt.test.internal.utils.GwtTestStringUtils;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Util.class)
public class UtilPatcher extends AutomaticPatcher {

	@PatchMethod
	public static int parseInt(String value, int defaultValue) {
		return GwtTestStringUtils.parseInt(value, defaultValue);
	}

}