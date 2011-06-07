package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.Scheduler;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Scheduler.class)
public class SchedulerPatcher extends AutomaticPatcher {

  static class OverrideScheduler extends Scheduler {

    private static Scheduler scheduler;

    public static Scheduler get() {
      if (scheduler == null) {
        scheduler = new OverrideScheduler();
      }

      return scheduler;
    }

    private OverrideScheduler() {

    }

    @Override
    public void scheduleDeferred(ScheduledCommand cmd) {
      cmd.execute();
    }

    @Override
    public void scheduleFinally(ScheduledCommand cmd) {
      cmd.execute();
    }

    @Override
    public void scheduleFixedDelay(RepeatingCommand cmd, int delayMs) {
      cmd.execute();
    }

    @Override
    public void scheduleFixedPeriod(RepeatingCommand cmd, int delayMs) {
      cmd.execute();
    }

    @Override
    public void scheduleIncremental(RepeatingCommand cmd) {
      cmd.execute();
    }

  }

  @PatchMethod
  public static Scheduler get() {
    return OverrideScheduler.get();
  }

}
