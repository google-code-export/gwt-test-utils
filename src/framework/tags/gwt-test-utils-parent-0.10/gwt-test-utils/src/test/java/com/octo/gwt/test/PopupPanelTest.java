package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.PopupPanel;

public class PopupPanelTest extends AbstractGWTTest {
	
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
