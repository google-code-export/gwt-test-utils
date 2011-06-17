package com.octo.gwt.test.uibinder;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class UiBinderWithDomTest extends GwtTestTest {

  @Test
  public void checkUiBinderDom() {
    // Arrange
    UiBinderWithDom helloWorld = new UiBinderWithDom();
    Document.get().getBody().appendChild(helloWorld.getElement());

    // Act
    helloWorld.setName("World");

    // Assert
    SpanElement nameSpan = GwtReflectionUtils.getPrivateFieldValue(helloWorld,
        "nameSpan");
    Assert.assertEquals("World", nameSpan.getInnerText());
    Assert.assertEquals("World",
        Document.get().getElementById("name").getInnerText());
  }
}
