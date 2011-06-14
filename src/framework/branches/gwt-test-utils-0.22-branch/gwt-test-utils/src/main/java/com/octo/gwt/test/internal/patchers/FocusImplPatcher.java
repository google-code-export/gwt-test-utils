package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(FocusImpl.class)
public class FocusImplPatcher {

  @PatchMethod
  public static void blur(FocusImpl focusImpl, Element element) {

  }

  @PatchMethod
  public static void focus(FocusImpl focusImpl, Element element) {

  }

  @PatchMethod
  public static int getTabIndex(FocusImpl focusImpl, Element elem) {
    return JavaScriptObjects.getInteger(elem, JsoProperties.TAB_INDEX);
  }

  @PatchMethod
  public static void setTabIndex(FocusImpl focusImpl, Element elem, int index) {
    JavaScriptObjects.setProperty(elem, JsoProperties.TAB_INDEX, index);
  }

}
