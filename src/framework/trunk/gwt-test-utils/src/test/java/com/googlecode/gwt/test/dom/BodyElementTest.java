package com.googlecode.gwt.test.dom;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.googlecode.gwt.test.GwtTestTest;

public class BodyElementTest extends GwtTestTest {

   private BodyElement b;

   @Test
   public void as() {
      // Act
      BodyElement asElement = BodyElement.as(b);

      // Assert
      assertEquals(b, asElement);
   }

   @Before
   public void initDocument() {
      b = (BodyElement) Document.get().createElement("body");
   }

}
