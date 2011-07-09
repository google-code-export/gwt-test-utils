package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class ButtonTest extends GwtTestTest {

  private Button b;

  @Before
  public void beforeButtonTest() {
    // create the button in a standard JVM
    b = new Button();

    // needs to be attached
    RootPanel.get().add(b);
  }

  @Test
  public void checkClickWithHander() {
    // Arrange
    // add a handler to test the click
    b.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        b.setHTML("clicked");
      }

    });

    // Pre-Assert
    Assert.assertEquals("", b.getHTML());

    // Act
    b.click();

    // Assert
    Assert.assertEquals("clicked", b.getHTML());
  }

  @Test
  public void checkClickWithListener() {
    // Arrange
    b.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        b.setHTML("clicked");

      }
    });

    // Pre-Assert
    Assert.assertEquals("", b.getHTML());

    // Act
    b.click();

    // Assert
    Assert.assertEquals("clicked", b.getHTML());
  }

  @Test
  public void checkEnable() {
    // Pre-Assert
    Assert.assertEquals(true, b.isEnabled());

    // Act
    b.setEnabled(false);

    // Assert
    Assert.assertEquals(false, b.isEnabled());
  }

  @Test
  public void checkHTML() {
    // Pre-Assert
    Assert.assertEquals("", b.getHTML());

    // Act
    b.setHTML("test-html");

    // Assert
    Assert.assertEquals("test-html", b.getHTML());
  }

  @Test
  public void checkStyleName() {
    // Pre-Assert
    Assert.assertEquals("gwt-Button", b.getStyleName());

    // Act
    b.setStyleName("test-button-style");

    // Assert
    Assert.assertEquals("test-button-style", b.getStyleName());
  }

  @Test
  public void checkStylePrimaryName() {
    // Act
    b.setStylePrimaryName("test-button-styleP");

    // Assert
    Assert.assertEquals("test-button-styleP", b.getStylePrimaryName());
  }

  @Test
  public void checkText() {
    // Act
    b.setText("toto");

    // Assert
    Assert.assertEquals("toto", b.getText());
  }

  @Test
  public void checkTitle() {
    // Act
    b.setTitle("title");

    // Assert
    Assert.assertEquals("title", b.getTitle());
  }

  @Test
  public void checkToString() {
    // Arrange
    b.setHTML("test button");
    b.setEnabled(false);
    b.setFocus(false);
    b.setAccessKey('h');
    b.setStyleName("my-style");

    // Act
    String toString = b.toString();

    // Assert
    Assert.assertEquals(
        "<button type=\"button\" class=\"my-style\" disabled=\"\" accesskey=\"h\">test button</button>",
        toString);
  }

  @Test
  public void checkVisible() {
    // Pre-Assert
    Assert.assertEquals(true, b.isVisible());

    // Act
    b.setVisible(false);

    // Assert
    Assert.assertEquals(false, b.isVisible());
  }

  @Test
  public void checkWrap() {
    // Arrange
    ButtonElement element = Document.get().createButtonElement();
    element.setTabIndex(3);

    // Act
    Button b = Button.wrap(element);

    // Assert 1
    Assert.assertEquals(0, b.getTabIndex());

    // Act 2
    b.setTabIndex(1);

    // Assert 2
    Assert.assertEquals(1, element.getTabIndex());
  }

}
