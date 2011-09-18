package com.octo.gwt.test.uibinder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class UiBinderWithDomTest extends GwtTestTest {

  @Test
  public void uiBinderDom() {
    // Arrange
    UiBinderWithDom helloWorld = new UiBinderWithDom();
    Document.get().getBody().appendChild(helloWorld.getElement());

    // Act
    helloWorld.setName("World");

    // Assert
    SpanElement nameSpan = GwtReflectionUtils.getPrivateFieldValue(helloWorld,
        "nameSpan");
    assertEquals("World", nameSpan.getInnerText());
    assertEquals("World", Document.get().getElementById("name").getInnerText());
  }
}
