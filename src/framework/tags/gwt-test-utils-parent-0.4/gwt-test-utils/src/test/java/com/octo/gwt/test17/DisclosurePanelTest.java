package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class DisclosurePanelTest extends AbstractGWTTest {

	@Test
	public void checkStyle() {
		DisclosurePanel dp = new DisclosurePanel();

		Assert.assertEquals("gwt-DisclosurePanel gwt-DisclosurePanel-closed", dp.getStyleName());

		dp.setOpen(true);

		Assert.assertEquals("gwt-DisclosurePanel gwt-DisclosurePanel-open", dp.getStyleName());
	}

	@Test
	public void checkTitle() {
		DisclosurePanel dp = new DisclosurePanel();
		dp.setTitle("title");
		Assert.assertEquals("title", dp.getTitle());
	}

	@Test
	public void checkVisible() {
		DisclosurePanel dp = new DisclosurePanel();
		Assert.assertEquals(true, dp.isVisible());
		dp.setVisible(false);
		Assert.assertEquals(false, dp.isVisible());
	}

}
