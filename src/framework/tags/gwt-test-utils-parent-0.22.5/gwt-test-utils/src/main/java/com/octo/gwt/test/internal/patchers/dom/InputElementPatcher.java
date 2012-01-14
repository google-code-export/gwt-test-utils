package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(InputElement.class)
class InputElementPatcher {

  @PatchMethod
  static void click(InputElement inputElement) {

  }

  @PatchMethod
  static void select(InputElement inputElement) {

  }

}
