package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.dom.client.Document;

public class DocumentTest extends GwtTestTest {

  @Test
  public void checkTitle() {
    // Act
    Document.get().setTitle("my title");

    // Assert
    Assert.assertEquals("my title", Document.get().getTitle());
  }

}
