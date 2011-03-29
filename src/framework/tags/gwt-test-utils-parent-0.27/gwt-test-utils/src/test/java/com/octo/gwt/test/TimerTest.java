package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.Timer;

public class TimerTest extends GwtTest {

  boolean bool;
  int i;

  @Test
  public void checkSchedule() throws Exception {
    bool = false;
    Timer timer = new Timer() {

      @Override
      public void run() {
        bool = !bool;
      }
    };

    timer.schedule(500);

    Assert.assertTrue("The token was not set after Timer has run", bool);
  }

  @Test
  public void checkScheduleRepeating() throws Exception {
    i = 0;
    Timer timer = new Timer() {

      @Override
      public void run() {
        i++;
      }
    };

    timer.scheduleRepeating(500);

    Assert.assertTrue("timer should be run more than once", i > 1);
  }
}
