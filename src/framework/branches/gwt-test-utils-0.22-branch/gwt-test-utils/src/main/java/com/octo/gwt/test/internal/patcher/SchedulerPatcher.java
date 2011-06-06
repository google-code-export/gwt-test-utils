package com.octo.gwt.test.internal.patcher;

import com.google.gwt.core.client.Scheduler;
import com.octo.gwt.test.internal.overrides.OverrideScheduler;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(Scheduler.class)
public class SchedulerPatcher extends AutomaticPatcher {

  @PatchMethod
  public static Scheduler get() {
    return OverrideScheduler.get();
  }

}
