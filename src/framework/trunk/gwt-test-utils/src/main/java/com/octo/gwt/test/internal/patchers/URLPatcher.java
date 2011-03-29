package com.octo.gwt.test.internal.patchers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.gwt.http.client.URL;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(URL.class)
public class URLPatcher extends AutomaticPatcher {

  @PatchMethod
  public static String encodeQueryStringImpl(String decodedURLComponent) {
    try {
      return URLEncoder.encode(decodedURLComponent, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

}
