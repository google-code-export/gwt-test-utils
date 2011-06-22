package com.octo.gwt.test;

/**
 * {@link GwtTest} class to test gwt-test-utils.
 * 
 * @author Gael Lazzari
 * 
 */
public abstract class GwtTestTest extends GwtTest {

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.GwtTestUtils";
  }

  @Override
  protected String getHostPagePath(String moduleFullQualifiedName) {
    return null;
  }

}
