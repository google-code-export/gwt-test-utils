package com.googlecode.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwt.test.GwtLogHandler;

public class GwtLogTest extends GwtTestTest {

  private String message;

  private Throwable t;

  @Override
  public GwtLogHandler getLogHandler() {
    return new GwtLogHandler() {

      public void log(String message, Throwable t) {
        GwtLogTest.this.message = message;
        GwtLogTest.this.t = t;
      }

    };
  }

  @Test
  public void log() {
    // Arrange
    message = null;
    t = null;
    Throwable throwable = new Exception("test");

    // Act
    GWT.log("toto", throwable);

    // Assert
    assertEquals("toto", message);
    assertEquals(throwable, t);
  }
}
