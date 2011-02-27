package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.util.Format;
import com.octo.gwt.test.internal.utils.GwtTestStringUtils;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Format.class)
public class FormatPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String camelize(String s) {
		return GwtTestStringUtils.camelize(s);
	}

	@PatchMethod
	public static String hyphenize(String name) {
		return GwtTestStringUtils.hyphenize(name);
	}

}
