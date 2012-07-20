package com.googlecode.gwt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.google.gwt.user.client.Cookies;

public class CookiesTest extends GwtTestTest {

   @Test
   public void cookies() {
      // Pre-Assert
      assertNull(Cookies.getCookie("test"));

      // Act 1
      Cookies.setCookie("test", "test-value");

      // Assert 1
      assertEquals("test-value", Cookies.getCookie("test"));

      // Act 2
      Cookies.removeCookie("test");

      // Assert 2
      assertNull(Cookies.getCookie("test"));
   }

}
