package com.octo.gwt.test.internal.utils;

import org.junit.Assert;
import org.junit.Test;

public class GwtTestStringUtilsTest {

  @Test
  public void checkDehyphenize() throws Exception {
    Assert.assertEquals("foo", GwtStringUtils.dehyphenize("foo"));
    Assert.assertEquals("fooBar", GwtStringUtils.dehyphenize("foo-bar"));
  }

  @Test
  public void checkHyphenize() throws Exception {
    Assert.assertEquals("foo", GwtStringUtils.hyphenize("foo"));
    Assert.assertEquals("foo-bar", GwtStringUtils.hyphenize("fooBar"));
  }

}
