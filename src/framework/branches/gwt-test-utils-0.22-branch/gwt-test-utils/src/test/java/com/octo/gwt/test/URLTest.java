package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.http.client.URL;

public class URLTest extends AbstractGwtTest {

  @Test
  public void checkEncodeComponent() {
    String stringToEncode = "test";

    Assert.assertEquals(stringToEncode, URL.encodeComponent(stringToEncode));
  }

}
