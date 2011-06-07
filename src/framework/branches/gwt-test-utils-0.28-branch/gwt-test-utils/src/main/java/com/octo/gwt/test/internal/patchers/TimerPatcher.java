package com.octo.gwt.test.internal.patchers;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Timer;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Timer.class)
public class TimerPatcher extends AutomaticPatcher {

  public static int DEFAULT_REPEAT_TIME = 5;
  private static Map<Timer, Integer> CACHE = new HashMap<Timer, Integer>();

  @PatchMethod
  public static void clearTimeout(int id) {

  }

  public static void reset() {
    CACHE.clear();
  }

  @PatchMethod
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

  @PatchMethod
  public static void scheduleRepeating(Timer timer, int periodMillis)
      throws Exception {
    if (periodMillis <= 0) {
      throw new IllegalArgumentException("must be positive");
    }

    if (!CACHE.containsKey(timer)) {
      CACHE.put(timer, 0);
    }

    int runTimes = CACHE.get(timer);
    while (runTimes < DEFAULT_REPEAT_TIME
        && CACHE.get(timer) < DEFAULT_REPEAT_TIME) {
      CACHE.put(timer, ++runTimes);
      timer.run();
    }

  }

}
