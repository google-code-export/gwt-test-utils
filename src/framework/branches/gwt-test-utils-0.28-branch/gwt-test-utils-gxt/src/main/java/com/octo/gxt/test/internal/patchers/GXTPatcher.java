package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.GXT;
import com.google.gwt.user.client.Window.Navigator;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(GXT.class)
class GXTPatcher {

  @PatchMethod
  static String getUserAgent() {
    return Navigator.getUserAgent();
  }

}
