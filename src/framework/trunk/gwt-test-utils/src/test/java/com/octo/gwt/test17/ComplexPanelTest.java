package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class ComplexPanelTest extends AbstractGWTTest {

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
