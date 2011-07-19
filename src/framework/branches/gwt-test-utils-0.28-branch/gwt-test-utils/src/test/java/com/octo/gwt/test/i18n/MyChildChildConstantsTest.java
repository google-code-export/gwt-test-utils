package com.octo.gwt.test.i18n;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtTestTest;

public class MyChildChildConstantsTest extends GwtTestTest {

  private MyChildChildConstants childChildConstants;

  @Test
  public void checkChildConstant() {
    // Act
    String hello = childChildConstants.hello();
    String valueWithoutDefaultAnnotationInChild = childChildConstants.valueWithoutDefaultAnnotationInChild();
    String valueWithoutLocale = childChildConstants.valueWithoutLocale();
    String valueWithoutLocaleToBeOverride = childChildConstants.valueWithoutLocaleToBeOverride();

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
    childChildConstants = GWT.create(MyChildChildConstants.class);
  }

}
