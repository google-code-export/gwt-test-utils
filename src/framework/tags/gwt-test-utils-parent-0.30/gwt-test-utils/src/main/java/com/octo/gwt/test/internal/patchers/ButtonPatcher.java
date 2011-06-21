package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.ui.Button;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.events.Browser;

@PatchClass(Button.class)
class ButtonPatcher {

  @PatchMethod
  static void click(Button button) {
    Browser.click(button);
  }

}
