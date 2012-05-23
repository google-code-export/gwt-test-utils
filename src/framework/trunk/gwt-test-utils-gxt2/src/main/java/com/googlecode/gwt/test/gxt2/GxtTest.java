package com.googlecode.gwt.test.gxt2;

import org.junit.After;

import com.googlecode.gwt.test.GwtTest;

/**
 * <p>
 * Base class for test classes which need to manipulate (directly or indirectly)
 * GXT 2.x components.
 * </p>
 */
public abstract class GxtTest extends GwtTest {

  @After
  public final void tearDownGxTest() throws Exception {
    GxtReset.get().reset();
  }

}
