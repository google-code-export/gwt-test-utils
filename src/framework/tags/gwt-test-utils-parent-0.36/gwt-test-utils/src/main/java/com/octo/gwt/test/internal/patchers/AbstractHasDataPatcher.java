package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.cellview.client.AbstractHasData;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(AbstractHasData.class)
class AbstractHasDataPatcher {

  @PatchMethod
  static void adopt(AbstractHasData<?> abstractHasData, Widget child) {
    GwtReflectionUtils.callPrivateMethod(child, "setParent", abstractHasData);
  }

  @PatchMethod
  static void doAttach(AbstractHasData<?> abstractHasData, Widget child) {
    GwtReflectionUtils.callPrivateMethod(child, "onAttach");
  }
  /*-{
  }
  child.@com.google.gwt.user.client.ui.Widget::onAttach()();
  }-*/;

  @PatchMethod
  static void doDetach(AbstractHasData<?> abstractHasData, Widget child) {
    GwtReflectionUtils.callPrivateMethod(child, "onDetach");
  }

}
