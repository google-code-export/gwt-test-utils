package com.googlecode.gwt.test.internal.patchers;

import com.google.gwt.core.client.Scheduler;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(Scheduler.class)
public class SchedulerPatcher {

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
      executeRepeatingCommand(cmd);
    }

    @Override
    public void scheduleIncremental(RepeatingCommand cmd) {
      executeRepeatingCommand(cmd);
    }

    private void executeRepeatingCommand(RepeatingCommand cmd) {
      boolean repeat = true;
      while (repeat) {
        repeat = cmd.execute();
      }
    }

  }

  @PatchMethod
  public static Scheduler get() {
    return OverrideScheduler.get();
  }

}
