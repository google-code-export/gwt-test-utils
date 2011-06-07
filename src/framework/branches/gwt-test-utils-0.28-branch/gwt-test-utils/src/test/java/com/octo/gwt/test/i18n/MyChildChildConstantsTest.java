package com.octo.gwt.test.i18n;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.internal.GwtConfig;

public class MyChildChildConstantsTest extends GwtTestTest {

  private MyChildChildConstants childChildConstants;

  @Test
  public void checkChildConstant() {
    // Setup
    GwtConfig.get().setLocale(Locale.ENGLISH);

    // Test
    String hello = childChildConstants.hello();
    String valueWithoutDefaultAnnotationInChild = childChildConstants.valueWithoutDefaultAnnotationInChild();
    String valueWithoutLocale = childChildConstants.valueWithoutLocale();
    String valueWithoutLocaleToBeOverride = childChildConstants.valueWithoutLocaleToBeOverride();

    // Asserts
    Assert.assertEquals("Hello english !", hello);
    Assert.assertEquals("Value in child default .properties",
        valueWithoutDefaultAnnotationInChild);
    Assert.assertEquals(
        "Value from a default .properties file, without locale",
        valueWithoutLocale);
    Assert.assertEquals("Value overriden by child in default .properties",
        valueWithoutLocaleToBeOverride);
  }

  @Before
  public void setupMyChildConstantsTest() {
    childChildConstants = GWT.create(MyChildChildConstants.class);
  }

}
