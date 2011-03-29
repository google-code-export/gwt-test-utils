package com.octo.gxt.test;

import org.junit.Test;

import com.extjs.gxt.samples.desktop.client.DesktopApp;
import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtTest;

public class DesktopAppTest extends GwtTest {

  @Test
  public void checkOnModuleLoad() {
    // Setup
    DesktopApp app = GWT.create(DesktopApp.class);

    // Test
    app.onModuleLoad();

    // Assert
  }

  @Override
  protected String getModuleName() {
    return "com.extjs.gxt.samples.desktop.DesktopApp";
  }
}
