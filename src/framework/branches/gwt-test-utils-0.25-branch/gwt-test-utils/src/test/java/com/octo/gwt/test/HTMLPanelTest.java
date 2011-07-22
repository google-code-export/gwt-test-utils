package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.HTMLPanel;

public class HTMLPanelTest extends GwtTestTest {

  @Test
  public void getElementById() {
    // Arrange
    HTMLPanel panel = new HTMLPanel(
        "<div id=\"childDiv\" class=\"myClass\">some text</div>");

    // Act
    DivElement childDiv = panel.getElementById("childDiv").cast();

    // Assert
    assertEquals("myClass", childDiv.getClassName());
    assertEquals("some text", childDiv.getInnerText());
  }

  @Test
  public void getInnerHTML() {
    // Arrange
    HTMLPanel panel = new HTMLPanel("<h1>Test</h1>");

    // Act & Assert
    assertEquals("<h1>Test</h1>", panel.getElement().getInnerHTML());
  }
}
