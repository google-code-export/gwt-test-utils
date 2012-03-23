package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.History;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(History.class)
class HistoryPatcher {

  @PatchMethod
  static void back() {
    HistoryImplPatcher.BROWSER_HISTORY.back();
  }

  @PatchMethod
  static void forward() {
    HistoryImplPatcher.BROWSER_HISTORY.forward();
  }

}
