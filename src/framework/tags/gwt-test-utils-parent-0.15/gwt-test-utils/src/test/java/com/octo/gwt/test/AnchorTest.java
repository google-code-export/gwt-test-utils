package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Anchor;

public class AnchorTest extends AbstractGwtTest {

	@Test
	public void checkTarget() {
		Anchor a = new Anchor();
		a.setFocus(true);

		a.setTarget("myTarget");
		Assert.assertEquals("myTarget", a.getTarget());
	}

	@Test
	public void checkText() {
		Anchor a = new Anchor("foo");
		Assert.assertEquals("foo", a.getText());
		a.setText("toto");
		a.setFocus(true);

		Assert.assertEquals("toto", a.getText());
	}

	@Test
	public void checkName() {
		Anchor a = new Anchor();
		a.setName("toto");

		Assert.assertEquals("toto", a.getName());
	}

	@Test
	public void checkVisible() {
		Anchor a = new Anchor();
		Assert.assertEquals(true, a.isVisible());
		a.setVisible(false);
		Assert.assertEquals(false, a.isVisible());
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

	@Test
	public void checkTabIndex() {
		Anchor a = new Anchor();
		a.setTabIndex(1);

		Assert.assertEquals(1, a.getTabIndex());
	}

	@Test
	public void checkHTML() {
		Anchor a = new Anchor("<h1>foo</h1>", true);
		Assert.assertEquals("<h1>foo</h1>", a.getHTML());

		a.setHTML("<h1>test</h1>");

		Assert.assertEquals("<h1>test</h1>", a.getHTML());
	}

	@Test
	public void checkTagName() {
		Anchor a = new Anchor();

		Assert.assertEquals("a", a.getElement().getTagName());
	}

}
