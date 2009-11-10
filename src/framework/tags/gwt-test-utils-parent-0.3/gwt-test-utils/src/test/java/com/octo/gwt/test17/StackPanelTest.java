package com.octo.gwt.test17;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;

public class StackPanelTest {
	
	@Test
	public void checkTitle() {
		StackPanel sp = new StackPanel();
		sp.setTitle("title");
		Assert.assertEquals("title", sp.getTitle());
	}
	
	@Test
	public void checkVisible() {
		StackPanel sp = new StackPanel();
		Assert.assertEquals(true, sp.isVisible());
		sp.setVisible(false);
		Assert.assertEquals(false, sp.isVisible());
	}

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
