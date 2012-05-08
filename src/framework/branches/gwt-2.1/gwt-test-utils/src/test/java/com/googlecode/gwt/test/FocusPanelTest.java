package com.googlecode.gwt.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.gwt.test.utils.events.Browser;

public class FocusPanelTest extends GwtTestTest {

  private Label child;
  private FocusPanel panel;
  private boolean test;

  @Before
  public void beforeFocusPanelTest() {
    child = new Label("focus panel's child widget");
    panel = new FocusPanel(child);
    test = false;
  }

  @Test
  public void click_EmptyPanel() {
    // Arrange
    panel = new FocusPanel();
    panel.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        test = true;
      }
    });

    // Act
    Browser.click(panel);

    // Assert
    Assert.assertTrue(test);
  }

  @Test
  public void click_WithChild() {
    // Arrange
    panel.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        test = true;
      }
    });

    child.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        child.setText("clicked");

      }
    });

    // Act
    Browser.click(panel);

    // Assert
    Assert.assertTrue(test);
    // click event should not be dispatched to the child widget
    Assert.assertEquals("focus panel's child widget", child.getText());
  }

}
