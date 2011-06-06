package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.Window.Location;

public class LocationTest extends AbstractGwtTest {

  @Test
  public void checkLocationInformations() {
    Assert.assertEquals("http", Location.getProtocol());
    Assert.assertEquals("80", Location.getPort());
  }

}
