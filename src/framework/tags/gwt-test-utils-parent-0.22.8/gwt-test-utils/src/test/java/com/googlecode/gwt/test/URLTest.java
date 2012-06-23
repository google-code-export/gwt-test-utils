package com.googlecode.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.http.client.URL;

public class URLTest extends GwtTestTest {

  @Test
  public void encodeQueryString() {
    // Arrange
    String stringToEncode = "test";

    assertEquals(stringToEncode, URL.encode(stringToEncode));
  }

}
