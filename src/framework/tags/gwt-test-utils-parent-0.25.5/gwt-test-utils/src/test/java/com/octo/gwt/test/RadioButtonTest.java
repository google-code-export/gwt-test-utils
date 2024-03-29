package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RadioButton;
import com.octo.gwt.test.utils.events.Browser;

public class RadioButtonTest extends GwtTestTest {

  private boolean tested;

  @Test
  public void click_ClickHandler() {
    // Arrange
    tested = false;
    RadioButton r = new RadioButton("myRadioGroup", "foo");
    r.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        tested = !tested;
      }

    });
    // Pre-Assert
    assertEquals(false, tested);

    // Act
    Browser.click(r);

    // Assert
    assertEquals(true, tested);
    assertEquals(true, r.getValue());
  }

  @Test
  public void click_Twice_ClickHandler() {
    // Arrange
    tested = false;
    RadioButton r1 = new RadioButton("myRadioGroup", "r1");
    RadioButton r2 = new RadioButton("myRadioGroup", "r2");
    r1.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        tested = !tested;
      }

    });

    r2.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        tested = !tested;
      }

    });
    // Pre-Assert
    assertEquals(false, tested);

    // Act
    Browser.click(r1);
    Browser.click(r1);

    // Assert
    assertEquals(false, tested);
    assertEquals(true, r1.getValue());
  }

  @Test
  public void html() {
    // Arrange
    RadioButton rb = new RadioButton("myRadioGroup", "<h1>foo</h1>", true);
    // Pre-Assert
    assertEquals("<h1>foo</h1>", rb.getHTML());

    // Act
    rb.setHTML("<h1>test</h1>");

    // Assert
    assertEquals("<h1>test</h1>", rb.getHTML());
    assertEquals(1, rb.getElement().getChild(1).getChildCount());
    HeadingElement h1 = rb.getElement().getChild(1).getChild(0).cast();
    assertEquals("H1", h1.getTagName());
    assertEquals("test", h1.getInnerText());
  }

  @Test
  public void name() {
    // Arrange
    RadioButton rb = new RadioButton("myRadioGroup", "foo");
    // Pre-Assert
    assertEquals("myRadioGroup", rb.getName());

    // Act
    rb.setName("name");

    // Assert
    assertEquals("name", rb.getName());
  }

  @Test
  public void radioButton_Group() {
    // Arrange
    RadioButton rb0 = new RadioButton("myRadioGroup", "foo");
    RadioButton rb1 = new RadioButton("myRadioGroup", "bar");
    RadioButton rb2 = new RadioButton("myRadioGroup", "baz");

    // Act
    rb1.setValue(true);

    // Assert;
    assertEquals(false, rb0.getValue());
    assertEquals(true, rb1.getValue());
    assertEquals(false, rb2.getValue());
  }

  @Test
  public void text() {
    // Arrange
    RadioButton rb = new RadioButton("myRadioGroup", "foo");
    // Pre-Assert
    assertEquals("foo", rb.getText());

    // Act
    rb.setText("text");

    // Assert
    assertEquals("text", rb.getText());
  }

  @Test
  public void title() {
    // Arrange
    RadioButton rb = new RadioButton("myRadioGroup", "foo");
    // Pre-Assert
    assertEquals("", rb.getTitle());

    // Act
    rb.setTitle("title");

    // Assert
    assertEquals("title", rb.getTitle());
  }

  @Test
  public void visible() {
    // Arrange
    RadioButton rb = new RadioButton("myRadioGroup", "foo");
    // Pre-Assert
    assertEquals(true, rb.isVisible());

    // Act
    rb.setVisible(false);

    // Assert
    assertEquals(false, rb.isVisible());
  }

}
