package com.octo.gwt.test.internal.patcher;

import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class DurationPatcher extends AutomaticPatcher {

	@PatchMethod
	public static double currentTimeMillis() {
		return System.currentTimeMillis();
	}

}
