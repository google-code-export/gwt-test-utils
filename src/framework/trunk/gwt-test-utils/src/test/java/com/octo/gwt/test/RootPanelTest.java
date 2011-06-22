package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class RootPanelTest extends GwtTestTest {

  @Test
  public void checkAdd() {
    // Arrange
    Label label = new Label();
    Assert.assertFalse(label.isAttached());

    // Act
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
