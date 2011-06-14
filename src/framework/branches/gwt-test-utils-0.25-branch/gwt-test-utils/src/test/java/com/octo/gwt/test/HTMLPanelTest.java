package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.HTMLPanel;

public class HTMLPanelTest extends GwtTestTest {

  @Test
  public void checkGetElementById() {
    // Arrange
    HTMLPanel panel = new HTMLPanel(
        "<div id=\"childDiv\" class=\"myClass\">some text</div>");

    // Act
    DivElement childDiv = panel.getElementById("childDiv").cast();

    // Assert
    Assert.assertEquals("myClass", childDiv.getClassName());
    Assert.assertEquals("some text", childDiv.getInnerText());
  }

  @Test
  public void checkInstanciation() {
    HTMLPanel panel = new HTMLPanel("<h1>Test</h1>");

    Assert.assertEquals("<h1>Test</h1>", panel.getElement().getInnerHTML());
  }
}
