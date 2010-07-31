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
