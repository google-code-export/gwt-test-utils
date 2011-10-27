package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.user.client.Window.Location;

public class LocationTest extends GwtTestTest {

  @Test
  public void assign() {
    // Act
    Location.assign("http://assign.location.com");

    // Assert
    assertEquals("http", Location.getProtocol());
    assertEquals("assign.location.com", Location.getHostName());
    assertEquals("80", Location.getPort());
    assertEquals("assign.location.com:80", Location.getHost());
    assertEquals("http://assign.location.com", Location.getHref());
  }

  @Test
  public void defaultLocation() {
    // Act & Assert
    assertEquals("http", Location.getProtocol());
    assertEquals("127.0.0.1", Location.getHostName());
    assertEquals("8888", Location.getPort());
    assertEquals("127.0.0.1:8888", Location.getHost());
    assertEquals("http://127.0.0.1:8888/gwt_test_utils_module/",
        Location.getHref());
  }

  @Test
  public void replace() {
    // Act
    Location.replace("http://replace.location.com");

    // Assert
    assertEquals("http", Location.getProtocol());
    assertEquals("replace.location.com", Location.getHostName());
    assertEquals("80", Location.getPort());
    assertEquals("replace.location.com:80", Location.getHost());
    assertEquals("http://replace.location.com", Location.getHref());
  }

}
