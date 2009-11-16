package com.octo.gwt.test17;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class StackPanelTest extends AbstractGWTTest {
	
	private int index = -1;
	
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
	
	@Test
	public void checkClick() {
	
		// Set up
		index = -1;
		StackPanel panel = new StackPanel() {
			
			@Override
			public void showStack(int index) {
				StackPanelTest.this.index = index;
			};
		};
		
		panel.add(new Anchor());
		panel.add(new Anchor());
		
		// Test
		click(panel, 1);

		// Assert that the "AbstractGWTTest.click(ComplexPanel, index)" method trigger the "showStack" method
		Assert.assertEquals(1, index);
	}

}
