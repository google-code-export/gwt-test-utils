package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTML;

public class HTMLTest extends GwtTestTest {

  @Test
  public void checkToString() {
    // Arrange
    HTML html = new HTML("this is a <b>great</b> test.<BR/>Enjoy!");

    // Act
    String result = html.toString();

    // Assert
    assertEquals(
        "<div class=\"gwt-HTML\">this is a <b>great</b> test.<br>Enjoy!</div>",
        result);
  }

  @Test
  public void html() {
    // Arrange
    HTML html = new HTML("this is a <b>great</b> test.<BR/>Enjoy!");

    // Act
    String result = html.getHTML();

    // Assert
    assertEquals("this is a <b>great</b> test.<br>Enjoy!", result);
  }

  @Test
  public void html_withAnchor() {
    // Arrange
    HTML widget = new HTML("<a href=\"foo\" target=\"bar\">baz</a>");

    // Act
    NodeList<Element> nodeList = widget.getElement().getElementsByTagName("a");

    // Assert
    assertEquals(1, nodeList.getLength());
    AnchorElement anchor = nodeList.getItem(0).cast();
    assertEquals("foo", anchor.getHref());
    assertEquals("bar", anchor.getTarget());
  }

  @Test
  public void text() {
    // Arrange
    HTML html = new HTML("this is a <b>great</b> test.<BR/>Enjoy!");

    // Act
    String result = html.getText();

    // Assert
    assertEquals("this is a great test.Enjoy!", result);

    // Act 2
    html.setText("override <b>not bold text</b>");

    // Assert 2
    assertEquals("override <b>not bold text</b>", html.getText());
  }

}
