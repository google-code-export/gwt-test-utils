package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.CheckBox;

public class CheckBoxTest extends AbstractGwtTest {

	@Test
	public void checkCheckBoxClick() {
		CheckBox cb = new CheckBox();
		cb.setValue(false);
		cb.setFocus(true);

		Assert.assertEquals(false, cb.getValue());

		//we must use the parent click method to simule the click without registering an handler
		click(cb);
		Assert.assertEquals(true, cb.getValue());
	}

	@Test
	public void checkName() {
		CheckBox cb = new CheckBox();
		cb.setName("name");
		Assert.assertEquals("name", cb.getName());
	}

	@Test
	public void checkText() {
		CheckBox cb = new CheckBox("foo");
		Assert.assertEquals("foo", cb.getText());
		cb.setText("text");
		Assert.assertEquals("text", cb.getText());
	}

	@Test
	public void checkTitle() {
		CheckBox cb = new CheckBox();
		cb.setTitle("title");
		Assert.assertEquals("title", cb.getTitle());
	}

	@Test
	public void checkHTML() {
		CheckBox cb = new CheckBox("<h1>foo</h1>", true);
		Assert.assertEquals("<h1>foo</h1>", cb.getHTML());
		cb.setHTML("<h1>test</h1>");
		Assert.assertEquals("<h1>test</h1>", cb.getHTML());
	}

	@Test
	public void checkVisible() {
		CheckBox cb = new CheckBox();
		Assert.assertEquals(true, cb.isVisible());
		cb.setVisible(false);
		Assert.assertEquals(false, cb.isVisible());
	}

	@Test
	public void checkChecked() {
		CheckBox cb = new CheckBox();
		Assert.assertEquals(false, cb.getValue());
		cb.setValue(true);
		Assert.assertEquals(true, cb.getValue());
	}

}
