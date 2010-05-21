package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.Cookies;

public class CookiesTest extends AbstractGWTTest {
	
	@Test
	public void testCookies() {
		Cookies.setCookie("test", "test-value");
		Assert.assertEquals("test-value", Cookies.getCookie("test"));
		
		Cookies.removeCookie("test");
		Assert.assertNull(Cookies.getCookie("test"));
	}

}
