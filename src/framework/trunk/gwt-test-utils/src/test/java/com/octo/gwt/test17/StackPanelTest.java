package com.octo.gwt.test17;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;

public class StackPanelTest {

	@Test
	public void checkStackPanel() {
		StackPanel panel = new StackPanel();
		panel.add(new Label("Foo"), "foo");
		
		Label label = new Label("Bar");
		panel.add(label, "bar");
		
		panel.add(new Label("Baz"), "baz");
		
		Assert.assertEquals(3,panel.getWidgetCount());
		Assert.assertEquals(label, panel.getWidget(1));
		Assert.assertEquals(1, panel.getWidgetIndex(label));

	}

}
