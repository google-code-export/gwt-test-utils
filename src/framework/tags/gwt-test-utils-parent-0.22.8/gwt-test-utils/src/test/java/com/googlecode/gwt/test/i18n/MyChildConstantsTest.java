package com.googlecode.gwt.test.i18n;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwt.test.GwtTestTest;

public class MyChildConstantsTest extends GwtTestTest {

  private MyChildConstants childConstants;

  @Before
  public void beforeMyChildConstantsTest() {
    childConstants = GWT.create(MyChildConstants.class);
    setLocale(Locale.ENGLISH);
  }

  @Test
  public void childConstant() {
    // Act
    String hello = childConstants.hello();
    String valueWithoutDefaultAnnotationInChild = childConstants.valueWithoutDefaultAnnotationInChild();
    String valueWithoutLocale = childConstants.valueWithoutLocale();
    String valueWithoutLocaleToBeOverride = childConstants.valueWithoutLocaleToBeOverride();

    // Assert
    assertEquals("Hello english !", hello);
    assertEquals("Value in child default .properties",
        valueWithoutDefaultAnnotationInChild);
    assertEquals("Value from a default .properties file, without locale",
        valueWithoutLocale);
    assertEquals("Value overriden by child in default .properties",
        valueWithoutLocaleToBeOverride);
  }

}
