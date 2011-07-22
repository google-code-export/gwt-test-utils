package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.utils.events.Browser;

public class PushButtonTest extends GwtTestTest {

  private boolean clicked;

  @Test
  public void click() {
    // Arrange
    clicked = false;

    final PushButton b = new PushButton("Up", "Down");

    // needs to be attached
    RootPanel.get().add(b);

    b.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        clicked = true;
      }
    });

    // Pre-Assert
    assertEquals("Up", b.getText());

    // Act
    Browser.click(b);

    // Assert
    assertTrue("PushedButton onClick was not triggered", clicked);
    assertEquals("Up", b.getText());
  }

}
