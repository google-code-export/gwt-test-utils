package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.PopupPanel;

public class PopupPanelTest extends GwtTestTest {

  @Test
  public void chechShow() {
    // Arrange
    PopupPanel popup = new PopupPanel();
    Assert.assertFalse(popup.isShowing());

    // Act
    popup.show();

    // Assert
    Assert.assertTrue(popup.isShowing());
  }

  @Test
  public void chechShowGlass() {
    // Arrange
    PopupPanel popup = new PopupPanel();
    popup.setGlassEnabled(true);
    Assert.assertFalse(popup.isShowing());

    // Act
    popup.show();

    // Assert
    Assert.assertTrue(popup.isShowing());
  }

  @Test
  public void chechVisible() {
    // Arrange
    PopupPanel popup = new PopupPanel();
    Assert.assertFalse(popup.isVisible());
    Assert.assertEquals("hidden",
        popup.getElement().getStyle().getProperty("visibility"));

    // Act
    popup.setVisible(true);

    // Assert
    Assert.assertTrue(popup.isVisible());
    Assert.assertEquals("visible",
        popup.getElement().getStyle().getProperty("visibility"));
  }

  @Test
  public void checkAutoHideEnabled() {
    PopupPanel popupPanel = new PopupPanel(true);
    Assert.assertTrue(popupPanel.isAutoHideEnabled());

    popupPanel.setAutoHideEnabled(false);
    Assert.assertFalse(popupPanel.isAutoHideEnabled());
  }

}
