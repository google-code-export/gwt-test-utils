package com.octo.gwt.test17.internal;

import com.google.gwt.user.client.Timer;

public class PatchTimer {

	public static int DEFAULT_REPEAT_TIME = 5;

	public static void schedule(Timer timer, int delayMillis) throws Exception {
		if (delayMillis <= 0) {
			throw new IllegalArgumentException("must be positive");
		}
		timer.run();
	}

	public static void scheduleRepeating(Timer timer, int periodMillis) throws Exception {
		if (periodMillis <= 0) {
			throw new IllegalArgumentException("must be positive");
		}

		for (int i = 0; i < DEFAULT_REPEAT_TIME; i++) {
			timer.run();
		}
	}
}
