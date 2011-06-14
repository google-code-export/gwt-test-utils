package com.octo.gwt.test.dom;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.GwtTestTest;

public class TextTest extends GwtTestTest {

  @Test
  public void checkToString() {
    // Arrange
    Text text = Document.get().createTextNode("some text");

    // Act
    String toString = text.toString();

    // Assert
    Assert.assertEquals("'some text'", toString);

  }

}
