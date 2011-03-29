package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.FocusImpl;

public class FocusImplTest extends GwtTest {

  private Element e;
  private FocusImpl focusImpl = FocusImpl.getFocusImplForWidget();

  @Test
  public void checkBlur() {
    // just check blur(element) does not throw any exception
    focusImpl.blur(e);
  }

  @Test
  public void checkCreateFocusable() {
    // Test
    Element elem = focusImpl.createFocusable();

    // Assert
    Assert.assertEquals("div", elem.getTagName());
  }

  @Test
  public void checkFocus() {
    // just check focus(element) does not throw any exception
    focusImpl.focus(e);
  }

  @Test
  public void checkTabIndex() {
    focusImpl.setTabIndex(e, 3);
    Assert.assertEquals(3, focusImpl.getTabIndex(e));
  }

  @Before
  public void setUpFocusImplTest() {
    e = Document.get().createAnchorElement().cast();
  }

}
