package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
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
		SimplePanel panel = new SimplePanel();

		Button b = new Button();
		panel.add(b);

		Button b2 = (Button) panel.getWidget();

		Assert.assertEquals(b, b2);
	}

	@Test
	public void checkRemove() {
		SimplePanel panel = new SimplePanel();

		Button b = new Button();
		panel.add(b);

		Assert.assertTrue(panel.remove(b));
	}

}
