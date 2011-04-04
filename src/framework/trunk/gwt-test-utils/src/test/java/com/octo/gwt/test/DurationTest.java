package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.Duration;

public class DurationTest extends GwtTestTest {

  @Test
  public void checkCurrentTimeMillis() {

    Assert.assertTrue("Duration should be greater than 0",
        Duration.currentTimeMillis() > 0);
  }

}
