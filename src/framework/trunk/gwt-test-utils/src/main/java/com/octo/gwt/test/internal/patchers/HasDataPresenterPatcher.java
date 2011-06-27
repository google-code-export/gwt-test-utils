package com.octo.gwt.test.internal.patchers;

import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(target = "com.google.gwt.user.cellview.client.HasDataPresenter")
class HasDataPresenterPatcher {

  @PatchMethod
  static void resolvePendingState(Object hasDataPresenter) {

  }

}
