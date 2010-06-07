package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.PopupPanel;

public class PopupPanelTest extends AbstractGwtTest {

	@Test
	public void checkAutoHideEnabled() {
		PopupPanel popupPanel = new PopupPanel(true);
		Assert.assertTrue(popupPanel.isAutoHideEnabled());

		popupPanel.setAutoHideEnabled(false);
		Assert.assertFalse(popupPanel.isAutoHideEnabled());
	}

	@Test
	public void chechVisible() {
		// Setup
		PopupPanel popup = new PopupPanel();
		Assert.assertFalse(popup.isVisible());
		Assert.assertEquals("hidden", popup.getElement().getStyle().getProperty("visibility"));

		// Test
		popup.setVisible(true);

		// Assert
		Assert.assertTrue(popup.isVisible());
		Assert.assertEquals("visible", popup.getElement().getStyle().getProperty("visibility"));
	}

	@Test
	public void chechShow() {
		// Setup
		PopupPanel popup = new PopupPanel();
		Assert.assertFalse(popup.isShowing());

		// Test
		popup.show();

		// Assert
		Assert.assertTrue(popup.isShowing());
	}

	@Test
	public void chechShowGlass() {
		// Setup
		PopupPanel popup = new PopupPanel();
		popup.setGlassEnabled(true);
		Assert.assertFalse(popup.isShowing());

		// Test
		popup.show();

		// Assert
		Assert.assertTrue(popup.isShowing());
	}

}
