package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;

public class ComplexPanelTest extends AbstractGwtTest {

	@Test
	public void checkTitle() {
		ComplexPanel panel = new FlowPanel();
		panel.setTitle("title");
		Assert.assertEquals("title", panel.getTitle());
	}

	@Test
	public void checkVisible() {
		ComplexPanel panel = new FlowPanel();
		Assert.assertEquals(true, panel.isVisible());
		panel.setVisible(false);
		Assert.assertEquals(false, panel.isVisible());
	}

	@Test
	public void checkCount() {
		ComplexPanel panel = new FlowPanel();
		panel.add(new Button());
		panel.add(new Button());

		Assert.assertEquals(2, panel.getWidgetCount());
	}

	@Test
	public void checkRemove() {
		ComplexPanel panel = new FlowPanel();
		Button b = new Button();
		panel.add(b);

		panel.remove(b);
		Assert.assertEquals(0, panel.getWidgetCount());
	}

	@Test
	public void checkRemoveIndex() {
		ComplexPanel panel = new FlowPanel();

		Button b1 = new Button();
		panel.add(b1);

		Button b2 = new Button();
		panel.add(b2);

		panel.remove(1);

		Assert.assertEquals(1, panel.getWidgetCount());
		Assert.assertEquals(b1, panel.getWidget(0));
		Assert.assertEquals(panel, b1.getParent());
	}

}
