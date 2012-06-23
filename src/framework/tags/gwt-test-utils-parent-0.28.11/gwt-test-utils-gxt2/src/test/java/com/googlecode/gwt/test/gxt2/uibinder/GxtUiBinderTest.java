package com.googlecode.gwt.test.gxt2.uibinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.gwt.test.GwtTest;
import com.googlecode.gwt.test.gxt2.handlers.GxtUiBinderCreateHandler;
import com.googlecode.gwt.test.utils.events.Browser;

public class GxtUiBinderTest extends GwtTest {

  private UiBinderApp app;

  @Override
  public String getModuleName() {
    return "com.googlecode.gwt.test.gxt2.uibinder.UiBinderApp";
  }

  @Before
  public void setupTest() {
    // add the custom GXT-UiBinder create handler
    addGwtCreateHandler(new GxtUiBinderCreateHandler());

    app = new UiBinderApp();
    app.onModuleLoad();

    // Pre-Assert
    assertFalse(app.content.isShown());
    assertEquals("Go Away!", app.content.button.getText());
  }

  @Test
  public void test() {
    // Act 1
    Browser.click(app.openButton);

    // Assert 1
    assertTrue(app.content.isShown());

    // Act 2
    Browser.click(app.content.button);

    // Assert 2
    assertFalse(app.content.isShown());
  }
}
