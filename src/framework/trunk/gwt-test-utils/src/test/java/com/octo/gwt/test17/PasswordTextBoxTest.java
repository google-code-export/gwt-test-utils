package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class PasswordTextBoxTest extends AbstractGWTTest {
	
	@Test
	public void checkText() {
		PasswordTextBox p = new PasswordTextBox();
		p.setText("text");
		
		Assert.assertEquals("text", p.getText());
	}
	
	@Test
	public void checkTitle() {
		PasswordTextBox p = new PasswordTextBox();
		p.setTitle("text");
		
		Assert.assertEquals("text", p.getTitle());
	}
	
	@Test
	public void checkName() {
		PasswordTextBox p = new PasswordTextBox();
		p.setName("name");
		
		Assert.assertEquals("name", p.getName());
	}

}
