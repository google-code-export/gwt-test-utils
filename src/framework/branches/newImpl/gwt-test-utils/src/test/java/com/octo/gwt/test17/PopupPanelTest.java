package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.PopupPanel;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class PopupPanelTest extends AbstractGWTTest {

	@Test
	public void checkAutoHideEnabled() {
		PopupPanel popupPanel = new PopupPanel(true);
		Assert.assertTrue(popupPanel.isAutoHideEnabled());

		popupPanel.setAutoHideEnabled(false);
		Assert.assertFalse(popupPanel.isAutoHideEnabled());
	}

}
