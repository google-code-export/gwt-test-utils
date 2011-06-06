package com.octo.gwt.test.dom;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.BRElement;
import com.google.gwt.dom.client.Document;
import com.octo.gwt.test.AbstractGwtTest;

public class BRElementTest extends AbstractGwtTest {

  private BRElement b;

  @Before
  public void initDocument() {
    b = Document.get().createBRElement();
  }

  @Test
  public void checkAs() {
    BRElement asElement = BRElement.as(b);
    Assert.assertEquals(b, asElement);
  }

}
