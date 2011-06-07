package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class ButtonTest extends GwtTestTest {

  @Test
  public void checkClickWithHander() {

    // create the button in a standard JVM
    final Button b = new Button();
    // add a handler to test the click
    b.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        b.setHTML("clicked");
      }

    });

    Assert.assertEquals("", b.getHTML());

    // simulate click
    b.click();

    // assert that the "AbstractGWTTest.click(Widget)" method trigger the
    // "onClick" handler's method
    Assert.assertEquals("clicked", b.getHTML());
  }

  @Test
  public void checkClickWithListener() {
    final Button b = new Button();

    b.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        b.setHTML("clicked");

      }
    });

    Assert.assertEquals("", b.getHTML());

    // simulate click
    b.click();

    Assert.assertEquals("clicked", b.getHTML());
  }

  @Test
  public void checkEnable() {
    Button b = new Button();
    Assert.assertEquals(true, b.isEnabled());

    b.setEnabled(false);

    Assert.assertEquals(false, b.isEnabled());
  }

  @Test
  public void checkHTML() {
    Button b = new Button("test");
    Assert.assertEquals("test", b.getHTML());

    b.setHTML("test-html");

    Assert.assertEquals("test-html", b.getHTML());
  }

  @Test
  public void checkStyleName() {
    Button b = new Button();
    Assert.assertEquals("gwt-Button", b.getStyleName());

    b.setStyleName("test-button-style");

    Assert.assertEquals("test-button-style", b.getStyleName());
  }

  @Test
  public void checkStylePrimaryName() {
    Button b = new Button();

    b.setStylePrimaryName("test-button-styleP");

    Assert.assertEquals("test-button-styleP", b.getStylePrimaryName());
  }

  @Test
  public void checkText() {
    Button b = new Button();
    b.setText("toto");

    Assert.assertEquals("toto", b.getText());
  }

  @Test
  public void checkTitle() {
    Button b = new Button();
    b.setTitle("title");

    Assert.assertEquals("title", b.getTitle());
  }

  @Test
  public void checkToString() {
    // Setup
    Button b = new Button("test button");
    b.setEnabled(false);
    b.setFocus(false);
    b.setAccessKey('h');
    b.setStyleName("my-style");

    // Test
    String toString = b.toString();

    // Assert
    Assert.assertEquals(
        "<button type=\"button\" class=\"my-style\" disabled=\"\" accesskey=\"h\">test button</button>",
        toString);
  }

  @Test
  public void checkVisible() {
    Button b = new Button();
    Assert.assertEquals(true, b.isVisible());

    b.setVisible(false);

    Assert.assertEquals(false, b.isVisible());
  }

  @Test
  public void checkWrap() {
    // Set up

    ButtonElement element = Document.get().createButtonElement();
    element.setTabIndex(3);

    // Test
    Button b = Button.wrap(element);

    // Assert 1
    Assert.assertEquals(3, b.getTabIndex());

    // Test 2
    b.setTabIndex(1);

    // Assert 2
    Assert.assertEquals(1, element.getTabIndex());
  }

}
