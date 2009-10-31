package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Anchor;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class AnchorTest extends AbstractGWTTest {

	@Test
	public void checkText() {
		Anchor a = new Anchor();
		a.setText("toto");

		Assert.assertEquals("toto", a.getText());
	}

	@Test
	public void checkHref() {
		Anchor a = new Anchor("toto", "href");
		a.setFocus(true);
		a.setAccessKey('k');

		Assert.assertEquals("toto", a.getText());
		Assert.assertEquals("href", a.getHref());
	}

	@Test
	public void checkAbsoluteLeft() {
		Anchor a = new Anchor();

		Assert.assertEquals(0, a.getAbsoluteLeft());
	}

	@Test
	public void checkAbsoluteTop() {
		Anchor a = new Anchor();

		Assert.assertEquals(0, a.getAbsoluteTop());
	}
	
	@Test
	public void checkTitle() {
		Anchor a = new Anchor();
		a.setTitle("title");

		Assert.assertEquals("title", a.getTitle());
	}



}
