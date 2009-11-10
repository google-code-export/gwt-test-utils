package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class PasswordTextBoxTest extends AbstractGWTTest {
	
	@Test
	public void checkName() {
		PasswordTextBox ptb = new PasswordTextBox();
		ptb.setName("name");
		Assert.assertEquals("name", ptb.getName());
	}
	
	@Test
	public void checkText() {
		PasswordTextBox ptb = new PasswordTextBox();
		ptb.setText("text");
		Assert.assertEquals("text", ptb.getText());
	}
	
	@Test
	public void checkTitle() {
		PasswordTextBox ptb = new PasswordTextBox();
		ptb.setTitle("title");
		Assert.assertEquals("title", ptb.getTitle());
	}
	
	@Test
	public void checkVisible() {
		PasswordTextBox ptb = new PasswordTextBox();
		Assert.assertEquals(true, ptb.isVisible());
		ptb.setVisible(false);
		Assert.assertEquals(false, ptb.isVisible());
	}

}
