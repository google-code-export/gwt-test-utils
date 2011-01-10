package com.octo.gwt.test.internal.patcher;

import com.google.gwt.core.client.Duration;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(Duration.class)
public class DurationPatcher extends AutomaticPatcher {

	@PatchMethod
	public static double currentTimeMillis() {
		return System.currentTimeMillis();
	}

}
