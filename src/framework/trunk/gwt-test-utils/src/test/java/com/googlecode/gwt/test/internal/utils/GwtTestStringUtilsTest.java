package com.googlecode.gwt.test.internal.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.gwt.test.internal.utils.GwtStringUtils;

public class GwtTestStringUtilsTest {

   @Test
   public void dehyphenize() throws Exception {
      assertEquals("foo", GwtStringUtils.dehyphenize("foo"));
      assertEquals("fooBar", GwtStringUtils.dehyphenize("foo-bar"));
   }

   @Test
   public void hyphenize() throws Exception {
      assertEquals("foo", GwtStringUtils.hyphenize("foo"));
      assertEquals("foo-bar", GwtStringUtils.hyphenize("fooBar"));
   }

   @Test
   public void treatDoubleValue() {
      assertEquals("250px", GwtStringUtils.treatDoubleValue("250px"));
      assertEquals("250.1px", GwtStringUtils.treatDoubleValue("250.1px"));
      assertEquals("250px", GwtStringUtils.treatDoubleValue("250.0px"));
      assertEquals("250.1 px", GwtStringUtils.treatDoubleValue("250.1 px"));
      assertEquals("250 px", GwtStringUtils.treatDoubleValue("250.0 px"));
      assertEquals("120.20202020202021px", GwtStringUtils.treatDoubleValue("120.20202020202021px"));
   }

}
