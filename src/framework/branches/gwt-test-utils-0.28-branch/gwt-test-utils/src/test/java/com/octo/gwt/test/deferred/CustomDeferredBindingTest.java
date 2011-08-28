package com.octo.gwt.test.deferred;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;

public class CustomDeferredBindingTest extends GwtTestTest {

  @Test
  public void create_generateWith() {
    try {
      GWT.create(IGenerateWith.class);
      fail();
    } catch (GwtTestConfigurationException e) {
      assertEquals(
          "A custom Generator should be used to instanciate 'com.octo.gwt.test.deferred.IGenerateWith', but gwt-test-utils does not support GWT compiler API, so you have to add our own GwtCreateHandler with 'GwtTest.addGwtCreateHandler(..)' method or to declare your tested object with @Mock",
          e.getMessage());
    }
  }

  @Test
  public void create_replaceWith() {
    // Act
    IReplaceWith replaceWith = GWT.create(IReplaceWith.class);

    assertTrue(replaceWith instanceof ReplaceWithDefault);
  }
}
