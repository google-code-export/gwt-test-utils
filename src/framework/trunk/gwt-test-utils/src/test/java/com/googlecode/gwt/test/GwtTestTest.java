package com.googlecode.gwt.test;

import com.googlecode.gwt.test.GwtTest;

/**
 * {@link GwtTest} class to test gwt-test-utils.
 * 
 * @author Gael Lazzari
 * 
 */
public abstract class GwtTestTest extends GwtTest {

  @Override
  public String getModuleName() {
    return "com.googlecode.gwt.test.GwtTestUtils";
  }

  @Override
  protected String getHostPagePath(String moduleFullQualifiedName) {
    return null;
  }

}
