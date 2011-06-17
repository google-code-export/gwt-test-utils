package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.Duration;

public class DurationTest extends GwtTestTest {

  Duration duration;

  @Test
  public void checkCurrentTimeMillis() {
    // Act
    double currentTimeMillis = Duration.currentTimeMillis();

    // Assert
    Assert.assertTrue(currentTimeMillis > 0);
  }

  @Test
  public void checkElapsedMillis() {
    // Act
    int elapsed = duration.elapsedMillis();

    // Assert
    Assert.assertTrue(elapsed > -1);
  }

  @Before
  public void setupDurationTest() {
    duration = new Duration();
  }

}
