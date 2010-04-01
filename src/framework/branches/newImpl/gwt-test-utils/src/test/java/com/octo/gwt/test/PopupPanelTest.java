package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.PopupPanel;
import com.octo.gwt.test.AbstractGWTTest;

public class PopupPanelTest extends AbstractGWTTest {

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
		
		//Test
		popup.setVisible(true);
		
		// Assert
		Assert.assertTrue(popup.isVisible());
	}
	
	@Test
	public void chechShow() {
		// Setup
		PopupPanel popup = new PopupPanel();
		Assert.assertFalse(popup.isShowing());
		
		//Test
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
		
		//Test
		popup.show();
		
		// Assert
		Assert.assertTrue(popup.isShowing());
	}

}
