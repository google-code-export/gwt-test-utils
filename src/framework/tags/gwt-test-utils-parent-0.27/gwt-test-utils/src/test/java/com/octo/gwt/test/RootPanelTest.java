package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class RootPanelTest extends GwtTestWithEasyMock {

  // @Mock
  // private Widget myWidget;
  //
  // @Test
  // public void checkRootPanel1() throws Exception {
  // // Setup
  // mockAddToParent(myWidget, RootPanel.get());
  //
  // replay();
  //
  // // Test
  // RootPanel.get().add(myWidget);
  //
  // // Assert
  // verify();
  // Assert.assertEquals(1, RootPanel.get().getWidgetCount());
  // Assert.assertEquals(myWidget, RootPanel.get().getWidget(0));
  // }
  //
  // @Test
  // public void checkRootPanel2() throws Exception {
  // // Setup
  // mockAddToParent(myWidget, RootPanel.get());
  //
  // replay();
  // // Test
  // RootPanel.get().add(myWidget);
  //
  // // Assert
  // verify();
  // Assert.assertEquals(1, RootPanel.get().getWidgetCount());
  // Assert.assertEquals(myWidget, RootPanel.get().getWidget(0));
  // }

  @Test
  public void checkAdd() {
    // Setup
    Label label = new Label();
    Assert.assertFalse(label.isAttached());

    // Test
    RootPanel.get().add(label);

    // Assert
    Assert.assertEquals(label, RootPanel.get().getWidget(0));
    Assert.assertTrue(label.isAttached());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void checkInit() {
    Assert.assertEquals(0, RootPanel.get().getWidgetCount());
    RootPanel.get().getWidget(0);
  }

}
