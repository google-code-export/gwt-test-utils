package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(FocusImpl.class)
class FocusImplPatcher {

  @PatchMethod
  static void blur(FocusImpl focusImpl, Element element) {

  }

  @PatchMethod
  static void focus(FocusImpl focusImpl, Element element) {

  }

  @PatchMethod
  static int getTabIndex(FocusImpl focusImpl, Element elem) {
    return JavaScriptObjects.getInteger(elem, JsoProperties.TAB_INDEX);
  }

  @PatchMethod
  static void setTabIndex(FocusImpl focusImpl, Element elem, int index) {
    JavaScriptObjects.setProperty(elem, JsoProperties.TAB_INDEX, index);
  }

}
