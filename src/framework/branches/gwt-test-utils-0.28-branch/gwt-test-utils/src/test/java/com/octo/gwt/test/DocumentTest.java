package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class DocumentTest extends GwtTestTest {

  @Test
  public void checkGetDocumentElement() {
    // Act
    Element documentElement = Document.get().getDocumentElement();

    // Assert
    Assert.assertEquals("HTML", documentElement.getNodeName());
  }

  @Test
  public void checkGetNodeName() {
    // Act
    String nodeName = Document.get().getNodeName();

    // Assert
    Assert.assertEquals("#document", nodeName);
  }

  @Test
  public void checkTitle() {
    // Act
    Document.get().setTitle("my title");

    // Assert
    Assert.assertEquals("my title", Document.get().getTitle());
  }

}
