package com.octo.gwt.test.internal.patchers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.gwt.http.client.URL;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(URL.class)
class URLPatcher {

  @PatchMethod
  static String encodeQueryStringImpl(String decodedURLComponent) {
    try {
      return URLEncoder.encode(decodedURLComponent, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new GwtTestPatchException(e);
    }
  }

}
