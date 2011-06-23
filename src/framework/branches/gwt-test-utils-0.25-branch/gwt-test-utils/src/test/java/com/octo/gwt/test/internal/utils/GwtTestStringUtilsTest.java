package com.octo.gwt.test.internal.utils;

import org.junit.Assert;
import org.junit.Test;

public class GwtTestStringUtilsTest {

  @Test
  public void dehyphenize() throws Exception {
    Assert.assertEquals("foo", GwtStringUtils.dehyphenize("foo"));
    Assert.assertEquals("fooBar", GwtStringUtils.dehyphenize("foo-bar"));
  }

  @Test
  public void hyphenize() throws Exception {
    Assert.assertEquals("foo", GwtStringUtils.hyphenize("foo"));
    Assert.assertEquals("foo-bar", GwtStringUtils.hyphenize("fooBar"));
  }

  @Test
  public void treatDoubleValue() {
    Assert.assertEquals("250px", GwtStringUtils.treatDoubleValue("250px"));
    Assert.assertEquals("250.1px", GwtStringUtils.treatDoubleValue("250.1px"));
    Assert.assertEquals("250px", GwtStringUtils.treatDoubleValue("250.0px"));
    Assert.assertEquals("250.1 px", GwtStringUtils.treatDoubleValue("250.1 px"));
    Assert.assertEquals("250 px", GwtStringUtils.treatDoubleValue("250.0 px"));
    Assert.assertEquals("120.20202020202021px",
        GwtStringUtils.treatDoubleValue("120.20202020202021px"));
  }

}
