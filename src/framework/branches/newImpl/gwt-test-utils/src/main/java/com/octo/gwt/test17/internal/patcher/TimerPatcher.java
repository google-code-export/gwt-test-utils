package com.octo.gwt.test17.internal.patcher;

import java.util.HashMap;
import java.util.Map;

import javassist.CtMethod;

import com.google.gwt.user.client.Timer;

public class TimerPatcher extends AbstractPatcher {

	public static int DEFAULT_REPEAT_TIME = 5;
	private static Map<Timer, Integer> CACHE = new HashMap<Timer, Integer>();

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "schedule")) {
			return callMethod("schedule", "this, $1");
		} else if (match(m, "scheduleRepeating")) {
			return callMethod("scheduleRepeating", "this, $1");
		} else if (match(m, "clearTimeout")) {
			return "";
		}


		return null;
	}
	
	public static void schedule(Timer timer, int delayMillis) throws Exception {
		if (delayMillis <= 0) {
			throw new IllegalArgumentException("must be positive");
		}

		if (!CACHE.containsKey(timer)) {
			CACHE.put(timer, 0);
		}

		int runTimes = CACHE.get(timer);
		if (runTimes < DEFAULT_REPEAT_TIME) {
			CACHE.put(timer, ++runTimes);
			timer.run();
		}

	}

	public static void scheduleRepeating(Timer timer, int periodMillis) throws Exception {
		if (periodMillis <= 0) {
			throw new IllegalArgumentException("must be positive");
		}

		if (!CACHE.containsKey(timer)) {
			CACHE.put(timer, 0);
		}

		int runTimes = CACHE.get(timer);
		while (runTimes < DEFAULT_REPEAT_TIME && CACHE.get(timer) < DEFAULT_REPEAT_TIME) {
			CACHE.put(timer, ++runTimes);
			timer.run();
		}

	}

	public static void clear() {
		CACHE.clear();
	}

}
