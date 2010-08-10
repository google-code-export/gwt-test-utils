package com.octo.gwt.test.internal.patcher;

import com.google.gwt.core.client.Scheduler;
import com.octo.gwt.test.internal.overrides.OverrideScheduler;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(Scheduler.class)
public class SchedulerPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Scheduler get() {
		return OverrideScheduler.get();
	}

}
