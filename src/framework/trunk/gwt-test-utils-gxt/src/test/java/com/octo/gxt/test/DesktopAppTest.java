package com.octo.gxt.test;

import org.junit.Test;

import com.extjs.gxt.samples.desktop.client.DesktopApp;
import com.google.gwt.core.client.GWT;

public class DesktopAppTest extends GwtGxtTest {

  @Test
  public void checkOnModuleLoad() {
    // Setup
    DesktopApp app = GWT.create(DesktopApp.class);

    // Test
    app.onModuleLoad();

    // Assert
  }

}
