package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.user.client.Window.Location;

public class LocationTest extends GwtTestTest {

  @Test
  public void locationInformations() {
    // Act & Assert
    assertEquals("http", Location.getProtocol());
    assertEquals("80", Location.getPort());
  }

}
