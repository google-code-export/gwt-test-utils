package com.octo.gwt.test.internal.utils;

import org.junit.Assert;
import org.junit.Test;

public class GwtTestStringUtilsTest {

	@Test
	public void checkHyphenize() throws Exception {
		Assert.assertEquals("foo", GwtTestStringUtils.hyphenize("foo"));
		Assert.assertEquals("foo-bar", GwtTestStringUtils.hyphenize("fooBar"));
	}

	@Test
	public void checkDehyphenize() throws Exception {
		Assert.assertEquals("foo", GwtTestStringUtils.dehyphenize("foo"));
		Assert.assertEquals("fooBar", GwtTestStringUtils.dehyphenize("foo-bar"));
	}

}
