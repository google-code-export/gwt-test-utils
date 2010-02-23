package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test.AbstractGWTTest;

public class ImageTest extends AbstractGWTTest {

	@Test
	public void checkURL() {
		Image i = new Image("http://my-url");

		Assert.assertEquals("http://my-url", i.getUrl());

		i.setUrl("newURL");

		Assert.assertEquals("newURL", i.getUrl());
	}

	@Test
	public void checkTitle() {
		Image i = new Image();
		i.setTitle("title");
		Assert.assertEquals("title", i.getTitle());
	}

	@Test
	public void checkVisible() {
		Image i = new Image();
		Assert.assertEquals(true, i.isVisible());
		i.setVisible(false);
		Assert.assertEquals(false, i.isVisible());
	}

	@Test
	public void checkWidthPx() {
		Image i = new Image();
		i.setWidth("20px");
		Assert.assertEquals(20, i.getWidth());
	}

	@Test
	public void checkWidthEm() {
		Image i = new Image();
		i.setWidth("20em");
		Assert.assertEquals(20, i.getWidth());
	}

	@Test
	public void checkWidth() {
		Image i = new Image();
		i.setWidth("20");
		Assert.assertEquals(20, i.getWidth());
	}

	@Test
	public void checkNullWidth() {
		Image i = new Image();
		Assert.assertEquals(0, i.getWidth());
	}

	@Test
	public void checkHeightPx() {
		Image i = new Image();
		i.setHeight("20px");
		Assert.assertEquals(20, i.getHeight());
	}

	@Test
	public void checkHeightEm() {
		Image i = new Image();
		i.setHeight("20em");
		Assert.assertEquals(20, i.getHeight());
	}

	@Test
	public void checkHeight() {
		Image i = new Image();
		i.setHeight("20");
		Assert.assertEquals(20, i.getHeight());
	}

	@Test
	public void checkNullHeight() {
		Image i = new Image();
		Assert.assertEquals(0, i.getHeight());
	}
}
