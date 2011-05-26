package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.HTML;

public class HTMLTest extends GwtTestTest {

  @Test
  public void checkHTML() {
    // Arrange
    HTML html = new HTML("this is a <b>great</b> test");

    // Act
    String result = html.getHTML();

    // Assert
    Assert.assertEquals("this is a <b>great</b> test", result);
  }

}
