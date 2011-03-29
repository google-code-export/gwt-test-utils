package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.Scheduler;
import com.octo.gwt.test.internal.overrides.OverrideScheduler;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Scheduler.class)
public class SchedulerPatcher extends AutomaticPatcher {

  @PatchMethod
  public static Scheduler get() {
    return OverrideScheduler.get();
  }

}
