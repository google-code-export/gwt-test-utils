package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class SimplePanelTest extends AbstractGWTTest {
	
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
