package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.Label;

public class LabelTest extends GwtTestTest {

  @Test
  public void checkDirection() {
    Label label = new Label();

    Assert.assertEquals(Direction.DEFAULT, label.getDirection());
    label.setDirection(Direction.RTL);

    Assert.assertEquals(Direction.RTL, label.getDirection());
  }

  @Test
  public void checkSetId() {
    // Arrange
    Label label = new Label();

    // Act
    label.getElement().setId("myId");

    // Assert
    Assert.assertEquals("myId", label.getElement().getAttribute("id"));
  }

  @Test
  public void checkText() {
    Label label = new Label("foo");
    Assert.assertEquals("foo", label.getText());
    label.setText("text");
    Assert.assertEquals("text", label.getText());
  }

  @Test
  public void checkTitle() {
    Label label = new Label();
    label.setTitle("title");
    Assert.assertEquals("title", label.getTitle());
  }

  @Test
  public void checkVisible() {
    Label label = new Label();
    Assert.assertEquals(true, label.isVisible());
    label.setVisible(false);
    Assert.assertEquals(false, label.isVisible());
  }

  @Test
  public void checkWordWrap() {
    Label label = new Label();
    Assert.assertFalse(label.getWordWrap());

    label.setWordWrap(true);

    Assert.assertEquals(true, label.getWordWrap());
  }

  @Test
  public void checkWrapLabel() {
    // Setup
    // Element.setInnerHTML & Document.get().getElementById are supposed to work
    Document.get().getBody().setInnerHTML("<div id=\"anId\"></div>");
    DivElement div = Document.get().getElementById("anId").cast();

    // Test
    Label label = Label.wrap(div);
    label.setText("My wrapped label !");

    // Assert
    Assert.assertEquals(div, label.getElement());
    Assert.assertEquals("My wrapped label !", div.getInnerText());
  }

}
