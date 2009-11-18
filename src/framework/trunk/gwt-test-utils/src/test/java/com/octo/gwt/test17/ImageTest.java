package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test17.test.AbstractGWTTest;

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
}
