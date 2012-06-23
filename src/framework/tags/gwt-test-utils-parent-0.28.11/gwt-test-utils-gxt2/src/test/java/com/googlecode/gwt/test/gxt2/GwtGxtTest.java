package com.googlecode.gwt.test.gxt2;


public abstract class GwtGxtTest extends GxtTest {

  @Override
  public String getModuleName() {
    return "com.extjs.gxt.samples.desktop.DesktopApp";
  }

  @Override
  protected String getHostPagePath(String moduleFullQualifiedName) {
    return "test.html";
  }

}
