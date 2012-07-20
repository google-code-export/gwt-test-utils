package com.googlecode.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class DocumentTest extends GwtTestTest {

   @Test
   public void getDocumentElement() {
      // Act
      Element documentElement = Document.get().getDocumentElement();

      // Assert
      assertEquals("HTML", documentElement.getNodeName());
   }

   @Test
   public void getNodeName() {
      // Act
      String nodeName = Document.get().getNodeName();

      // Assert
      assertEquals("#document", nodeName);
   }

   @Test
   public void title() {
      // Act
      Document.get().setTitle("my title");

      // Assert
      assertEquals("my title", Document.get().getTitle());
   }

}
