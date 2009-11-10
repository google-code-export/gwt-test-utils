package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class TextBoxTest extends AbstractGWTTest {
	
	@Test
	public void checkName() {
		TextBox t = new TextBox();
		t.setName("name");
		Assert.assertEquals("name", t.getName());
	}
	
	@Test
	public void checkText() {
		TextBox t = new TextBox();
		t.setText("text");
		Assert.assertEquals("text", t.getText());
	}
	
	@Test
	public void checkTitle() {
		TextBox t = new TextBox();
		t.setTitle("title");
		Assert.assertEquals("title", t.getTitle());
	}
	
	@Test
	public void checkVisible() {
		TextBox t = new TextBox();
		Assert.assertEquals(true, t.isVisible());
		t.setVisible(false);
		Assert.assertEquals(false, t.isVisible());
	}
	
	@Test
	public void checkMaxLength() {
		TextBox t = new TextBox();
		t.setMaxLength(10);
		
		Assert.assertEquals(10, t.getMaxLength());
	}

}
