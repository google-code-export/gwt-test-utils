package com.octo.gxt.test;

import com.octo.gwt.test.GwtTest;

public abstract class GwtGxtTest extends GwtTest {

  @Override
  public String getModuleName() {
    return "com.extjs.gxt.samples.desktop.DesktopApp";
  }

  @Override
  protected String getHostPagePath(String moduleFullQualifiedName) {
    return null;
  }

}
