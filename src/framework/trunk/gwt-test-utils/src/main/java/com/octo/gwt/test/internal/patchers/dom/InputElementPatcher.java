package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(InputElement.class)
public class InputElementPatcher extends OverlayPatcher {

  @PatchMethod
  public static void click(InputElement inputElement) {

  }

  @PatchMethod
  public static void select(InputElement inputElement) {

  }

}
