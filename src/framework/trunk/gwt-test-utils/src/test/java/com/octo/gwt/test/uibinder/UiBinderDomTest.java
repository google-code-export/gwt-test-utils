package com.octo.gwt.test.uibinder;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class UiBinderDomTest extends GwtTestTest {

  @Test
  public void checkUiBinderDom() {
    // Setup
    UiBinderDom helloWorld = new UiBinderDom();
    Document.get().getBody().appendChild(helloWorld.getElement());

    // Test
    helloWorld.setName("World");

    // Asserts
    SpanElement nameSpan = GwtReflectionUtils.getPrivateFieldValue(helloWorld,
        "nameSpan");
    Assert.assertEquals("World", nameSpan.getInnerText());
    Assert.assertEquals("World",
        Document.get().getElementById("name").getInnerText());
  }
}
