package com.octo.gxt.test.internal.patchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.extjs.gxt.ui.client.util.Util;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Util.class)
public class UtilPatcher extends AutomaticPatcher {

	private static Pattern NUMBER_PATTERN = Pattern.compile("^\\s*(\\d+).*$");

	@PatchMethod
	public static int parseInt(String value, int defaultValue) {
		Matcher m = NUMBER_PATTERN.matcher(value);
		if (m.matches()) {
			return Integer.parseInt(m.group(1));
		} else {
			return defaultValue;
		}
	}

}
