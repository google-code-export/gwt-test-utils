package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.http.client.URL;

public class URLTest extends GwtTestTest {

  @Test
  public void eEncodeComponent() {
    // Arrange
    String stringToEncode = "test";

    // Act & Assert
    assertEquals(stringToEncode, URL.encodeQueryString(stringToEncode));
  }

}
