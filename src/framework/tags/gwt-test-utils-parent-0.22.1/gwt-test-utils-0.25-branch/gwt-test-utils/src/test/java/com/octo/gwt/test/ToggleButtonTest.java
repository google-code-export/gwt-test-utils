package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ToggleButton;
import com.octo.gwt.test.utils.events.Browser;

public class ToggleButtonTest extends GwtTestTest {

  private boolean clicked;

  @Test
  public void checkClick() {
    // Arrange
    final ToggleButton toggleButton = new ToggleButton("Up", "Down");
    clicked = false;

    toggleButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        clicked = true;
      }
    });

    // Pre-Assert
    Assert.assertFalse("ToggleButton should not be toggled by default",
        toggleButton.isDown());
    Assert.assertEquals("Up", toggleButton.getText());

    // Act
    Browser.click(toggleButton);

    // Assert
    Assert.assertTrue("ToggleButton onClick was not triggered", clicked);
    Assert.assertTrue(
        "ToggleButton should be toggled after being clicked once",
        toggleButton.isDown());
    Assert.assertEquals("Down", toggleButton.getText());

    // Act 2
    Browser.click(toggleButton);
    Assert.assertFalse(
        "ToggleButton should not be toggled after being clicked twice",
        toggleButton.isDown());
    Assert.assertEquals("Up", toggleButton.getText());
  }

}
