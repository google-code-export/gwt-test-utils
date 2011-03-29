package com.octo.gwt.test.dom;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.BaseElement;
import com.google.gwt.dom.client.Document;
import com.octo.gwt.test.GwtTest;

public class BaseElementTest extends GwtTest {

  private BaseElement b;

  @Test
  public void checkAs() {
    BaseElement asElement = BaseElement.as(b);
    Assert.assertEquals(b, asElement);
  }

  @Test
  public void checkHref() {
    Assert.assertEquals("", b.getHref());
    // Set up
    b.setHref("Href");

    // Assert
    Assert.assertEquals("Href", b.getHref());
  }

  @Test
  public void checkTarget() {
    Assert.assertEquals("", b.getTarget());
    // Set up
    b.setTarget("Target");

    // Assert
    Assert.assertEquals("Target", b.getTarget());
  }

  @Before
  public void initDocument() {
    b = Document.get().createBaseElement();
  }

}
