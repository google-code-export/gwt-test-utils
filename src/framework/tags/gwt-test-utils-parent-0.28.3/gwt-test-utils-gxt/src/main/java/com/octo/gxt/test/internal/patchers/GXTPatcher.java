package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.GXT;
import com.google.gwt.user.client.Window.Navigator;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(GXT.class)
public class GXTPatcher extends AutomaticPatcher {

  @PatchMethod
  public static String getUserAgent() {
    return Navigator.getUserAgent();
  }

}
