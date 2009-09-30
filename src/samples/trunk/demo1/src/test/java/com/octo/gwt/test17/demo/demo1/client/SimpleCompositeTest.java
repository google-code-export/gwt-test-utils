package com.octo.gwt.test17.demo.demo1.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class SimpleCompositeTest extends AbstractGWTTest {
	
	private SimpleComposite module;
	
	@Before
	public void init() throws Exception {
		module = new SimpleComposite();
	}
	
	@Test
	public void checkMouseMoveOnPicture() {
		Image img = ReflectionUtils.getPrivateFieldValue(module, "img");
		Label label = ReflectionUtils.getPrivateFieldValue(module, "label");
		
		Assert.assertEquals(null, label.getText());
		
		mouseMove(img);
		
		Assert.assertEquals("mouse moved on picture !", label.getText());
	}
	
	@Test
	public void checkDisplayClick() {
		
		Button button = ReflectionUtils.getPrivateFieldValue(module, "button");
		Label label = ReflectionUtils.getPrivateFieldValue(module, "label");
		ListBox listBox = ReflectionUtils.getPrivateFieldValue(module, "listBox");
		listBox.setSelectedIndex(1);
		
		Assert.assertEquals(null, label.getText());
		Assert.assertEquals("Bar", listBox.getItemText(listBox.getSelectedIndex()));
		
		//simule the click
		click(button);
		
		Assert.assertEquals("The button was clicked with value : Bar", label.getText());
	}

}
