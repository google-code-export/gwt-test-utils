package com.octo.gwt.test.internal.patcher;

import com.google.gwt.core.client.Duration;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(Duration.class)
public class DurationPatcher extends AutomaticPatcher {

	@PatchMethod
	public static double currentTimeMillis() {
		return System.currentTimeMillis();
	}

}
