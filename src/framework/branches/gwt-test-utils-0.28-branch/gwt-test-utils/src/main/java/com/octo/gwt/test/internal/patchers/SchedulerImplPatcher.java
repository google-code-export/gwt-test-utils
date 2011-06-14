package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.client.impl.SchedulerImpl;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(SchedulerImpl.class)
public class SchedulerImplPatcher {

  @PatchMethod
  public static void scheduleDeferred(SchedulerImpl impl, ScheduledCommand cmd) {
    cmd.execute();
  }

  @PatchMethod
  public static void scheduleEntry(SchedulerImpl impl, RepeatingCommand cmd) {
    cmd.execute();
  }

  @PatchMethod
  public static void scheduleEntry(SchedulerImpl impl, ScheduledCommand cmd) {
    cmd.execute();
  }

  @PatchMethod
  public static void scheduleFinally(SchedulerImpl impl, RepeatingCommand cmd) {
    cmd.execute();
  }

  @PatchMethod
  public static void scheduleFinally(SchedulerImpl impl, ScheduledCommand cmd) {
    cmd.execute();
  }

  @PatchMethod
  public static void scheduleFixedDelay(SchedulerImpl impl,
      RepeatingCommand cmd, int delayMs) {
    cmd.execute();
  }

  @PatchMethod
  public static void scheduleFixedPeriod(SchedulerImpl impl,
      RepeatingCommand cmd, int delayMs) {
    cmd.execute();
  }

  @PatchMethod
  public static void scheduleIncremental(SchedulerImpl impl,
      RepeatingCommand cmd) {
    cmd.execute();
  }

}
