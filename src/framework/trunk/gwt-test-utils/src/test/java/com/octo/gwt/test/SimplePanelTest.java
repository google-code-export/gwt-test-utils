package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class SimplePanelTest extends AbstractGwtTest {

	@Test
	public void checkTitle() {
		SimplePanel sp = new SimplePanel();
		sp.setTitle("title");
		Assert.assertEquals("title", sp.getTitle());
	}

	@Test
	public void checkVisible() {
		SimplePanel sp = new SimplePanel();
		Assert.assertEquals(true, sp.isVisible());
		sp.setVisible(false);
		Assert.assertEquals(false, sp.isVisible());
	}

	@Test
	public void checkAdd() {
		// Setup
		SimplePanel panel = new SimplePanel();
		RootPanel.get().add(panel);
		Assert.assertTrue(panel.isAttached());
		Assert.assertNull(panel.getWidget());
		Button b1 = new Button();
		Assert.assertFalse(b1.isAttached());
		Assert.assertNull(b1.getParent());

		// Test
		panel.add(b1);

		// Assert
		Assert.assertTrue(b1.isAttached());
		Assert.assertEquals(panel, b1.getParent());
		Assert.assertEquals(b1, panel.getWidget());
	}

	@Test
	public void checkRemove() {
		SimplePanel panel = new SimplePanel();

		Button b = new Button();
		panel.add(b);

		Assert.assertTrue(panel.remove(b));
	}

}
