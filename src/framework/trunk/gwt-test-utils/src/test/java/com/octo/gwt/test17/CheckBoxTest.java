package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.CheckBox;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class CheckBoxTest extends AbstractGWTTest {

	@Test
	public void checkCheckBoxClick() {
		final CheckBox cb = new CheckBox();
		cb.setValue(false);
		cb.setFocus(true);
		
		Assert.assertEquals(false, cb.getValue());
		
		//we must use the parent click method to simule the click without registering an handler
		click(cb);
		Assert.assertEquals(true, cb.getValue());
	}
	
	@Test
	public void checkName() {
		final CheckBox cb = new CheckBox();
		cb.setName("name");
		Assert.assertEquals("name", cb.getName());
	}
	
	@Test
	public void checkText() {
		final CheckBox cb = new CheckBox("foo");
		Assert.assertEquals("foo", cb.getText());
		cb.setText("text");
		Assert.assertEquals("text", cb.getText());
	}
	
	@Test
	public void checkTitle() {
		final CheckBox cb = new CheckBox();
		cb.setTitle("title");
		Assert.assertEquals("title", cb.getTitle());
	}
	
	@Test
	public void checkHTML() {
		final CheckBox cb = new CheckBox("<h1>foo</h1>", true);
		Assert.assertEquals("<h1>foo</h1>", cb.getHTML());
		cb.setHTML("<h1>test</h1>");
		Assert.assertEquals("<h1>test</h1>", cb.getHTML());
	}
	
	@Test
	public void checkChecked() {
		final CheckBox cb = new CheckBox();
		Assert.assertEquals(false, cb.getValue());
		cb.setValue(true);
		Assert.assertEquals(true, cb.getValue());
	}

}
