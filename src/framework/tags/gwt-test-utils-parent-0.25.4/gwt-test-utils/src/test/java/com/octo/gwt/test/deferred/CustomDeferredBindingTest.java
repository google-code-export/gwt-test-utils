package com.octo.gwt.test.deferred;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.utils.events.Browser;

public class CustomDeferredBindingTest extends GwtTestTest {

  @Test
  public void create_generateWith() {
    try {
      // Act
      GWT.create(IGenerateWith.class);
      fail();
    } catch (GwtTestConfigurationException e) {
      // Assert
      assertEquals(
          "A custom Generator should be used to instanciate 'com.octo.gwt.test.deferred.IGenerateWith', but gwt-test-utils does not support GWT compiler API, so you have to add our own GwtCreateHandler with 'GwtTest.addGwtCreateHandler(..)' method or to declare your tested object with @Mock",
          e.getMessage());
    }
  }

  @Test
  public void create_replaceWith_Default() {
    // Act
    IReplaceWith replaceWith = GWT.create(IReplaceWith.class);

    // Assert
    assertTrue(replaceWith instanceof ReplaceWithDefault);
  }

  @Test
  public void create_replaceWith_gecko() {
    // Arrange
    Browser.setProperty("user.agent", "gecko");

    // Act
    IReplaceWith replaceWith = GWT.create(IReplaceWith.class);

    // Assert
    assertTrue(replaceWith instanceof ReplaceWithMozilla);
  }

  @Test
  public void create_replaceWith_gecko1_8() {
    // Arrange
    Browser.setProperty("user.agent", "gecko1_8");

    // Act
    IReplaceWith replaceWith = GWT.create(IReplaceWith.class);

    // Assert
    assertTrue(replaceWith instanceof ReplaceWithMozilla);
  }

  @Test
  public void create_replaceWith_ie6() {
    // Arrange
    Browser.setProperty("user.agent", "ie6");

    // Act
    IReplaceWith replaceWith = GWT.create(IReplaceWith.class);

    // Assert
    assertTrue(replaceWith instanceof ReplaceWithIE);
  }

  @Test
  public void create_replaceWith_ie8() {
    // Arrange
    Browser.setProperty("user.agent", "ie8");

    // Act
    IReplaceWith replaceWith = GWT.create(IReplaceWith.class);

    // Assert
    assertTrue(replaceWith instanceof ReplaceWithDefault);
  }
}
