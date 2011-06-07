package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.HTML;

public class HTMLTest extends GwtTestTest {

  @Test
  public void checkHTML() {
    // Arrange
    HTML html = new HTML("this is a <b>great</b> test.<BR/>Enjoy!");

    // Act
    String result = html.getHTML();

    // Assert
    Assert.assertEquals("this is a <b>great</b> test.<br>Enjoy!", result);
  }

  @Test
  public void checkText() {
    // Arrange
    HTML html = new HTML("this is a <b>great</b> test.<BR/>Enjoy!");

    // Act
    String result = html.getText();

    // Assert
    Assert.assertEquals("this is a great test.Enjoy!", result);

    // Act 2
    html.setText("override <b>not bold text</b>");

    // Assert 2
    Assert.assertEquals("override <b>not bold text</b>", html.getText());
  }

  @Test
  public void checkToString() {
    // Arrange
    HTML html = new HTML("this is a <b>great</b> test.<BR/>Enjoy!");

    // Act
    String result = html.toString();

    // Assert
    Assert.assertEquals(
        "<div class=\"gwt-HTML\">this is a <b>great</b> test.<br>Enjoy!</div>",
        result);
  }

}
