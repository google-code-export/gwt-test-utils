package com.octo.gwt.test.i18n;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtTestTest;

public class MyChildChildConstantsTest extends GwtTestTest {

  private MyChildChildConstants childChildConstants;

  @Before
  public void beforeMyChildConstantsTest() {
    childChildConstants = GWT.create(MyChildChildConstants.class);
  }

  @Test
  public void childChildConstant() {
    // Act
    String hello = childChildConstants.hello();
    String valueWithoutDefaultAnnotationInChild = childChildConstants.valueWithoutDefaultAnnotationInChild();
    String valueWithoutLocale = childChildConstants.valueWithoutLocale();
    String valueWithoutLocaleToBeOverride = childChildConstants.valueWithoutLocaleToBeOverride();

    // Assert
    assertEquals("Hello english !", hello);
    assertEquals("Value in child default .properties",
        valueWithoutDefaultAnnotationInChild);
    assertEquals("Value from a default .properties file, without locale",
        valueWithoutLocale);
    assertEquals("Value overriden by child in default .properties",
        valueWithoutLocaleToBeOverride);
  }

  @Override
  public Locale getLocale() {
    return Locale.ENGLISH;
  }

}
