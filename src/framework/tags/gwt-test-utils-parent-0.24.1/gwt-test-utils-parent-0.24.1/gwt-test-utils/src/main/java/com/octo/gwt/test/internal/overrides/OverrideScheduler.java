package com.octo.gwt.test.internal.overrides;

import com.google.gwt.core.client.Scheduler;

public class OverrideScheduler extends Scheduler {

	private static Scheduler scheduler;

	public static Scheduler get() {
		if (scheduler == null) {
			scheduler = new OverrideScheduler();
		}

		return scheduler;
	}

	private OverrideScheduler() {

	}

	public void scheduleDeferred(ScheduledCommand cmd) {
		cmd.execute();
	}

	public void scheduleFinally(ScheduledCommand cmd) {
		cmd.execute();
	}

	public void scheduleFixedDelay(RepeatingCommand cmd, int delayMs) {
		cmd.execute();
	}

	public void scheduleFixedPeriod(RepeatingCommand cmd, int delayMs) {
		cmd.execute();
	}

	public void scheduleIncremental(RepeatingCommand cmd) {
		cmd.execute();
	}
	
	public void scheduleEntry(RepeatingCommand cmd) {
		cmd.execute();
	}

	public void scheduleEntry(ScheduledCommand cmd) {
		cmd.execute();
	}

	public void scheduleFinally(RepeatingCommand cmd) {
		cmd.execute();
	}

}
