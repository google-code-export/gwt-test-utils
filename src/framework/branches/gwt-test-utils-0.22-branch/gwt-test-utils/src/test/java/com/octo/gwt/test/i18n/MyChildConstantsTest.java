package com.octo.gwt.test.i18n;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtTestTest;

public class MyChildConstantsTest extends GwtTestTest {

  private MyChildConstants childConstants;

  @Test
  public void checkChildConstant() {
    // Act
    String hello = childConstants.hello();
    String valueWithoutDefaultAnnotationInChild = childConstants.valueWithoutDefaultAnnotationInChild();
    String valueWithoutLocale = childConstants.valueWithoutLocale();
    String valueWithoutLocaleToBeOverride = childConstants.valueWithoutLocaleToBeOverride();

    // Assert
    Assert.assertEquals("Hello english !", hello);
    Assert.assertEquals("Value in child default .properties",
        valueWithoutDefaultAnnotationInChild);
    Assert.assertEquals(
        "Value from a default .properties file, without locale",
        valueWithoutLocale);
    Assert.assertEquals("Value overriden by child in default .properties",
        valueWithoutLocaleToBeOverride);
  }

  @Override
  public Locale getLocale() {
    return Locale.ENGLISH;
  }

  @Before
  public void setupMyChildConstantsTest() {
    childConstants = GWT.create(MyChildConstants.class);
  }
}
