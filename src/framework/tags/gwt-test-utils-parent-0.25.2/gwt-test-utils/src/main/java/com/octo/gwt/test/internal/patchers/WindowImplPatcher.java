package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.impl.WindowImpl;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(WindowImpl.class)
class WindowImplPatcher {

  @PatchMethod
  static String getHash(WindowImpl windowImpl) {
    return "";
  }

  @PatchMethod
  static String getQueryString(WindowImpl windowImpl) {
    return "";
  }

  @PatchMethod
  static void initWindowCloseHandler(WindowImpl windowImpl) {

  }

  @PatchMethod
  static void initWindowResizeHandler(WindowImpl windowImpl) {

  }

  @PatchMethod
  static void initWindowScrollHandler(WindowImpl windowImpl) {

  }

}
