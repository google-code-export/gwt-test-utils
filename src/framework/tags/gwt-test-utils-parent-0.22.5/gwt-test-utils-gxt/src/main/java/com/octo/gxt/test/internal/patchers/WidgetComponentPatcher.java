package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.widget.WidgetComponent;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(WidgetComponent.class)
class WidgetComponentPatcher {

  @PatchMethod
  static void setParent(final WidgetComponent component, final Widget parent,
      final Widget child) {
    GwtReflectionUtils.setPrivateFieldValue(child, "parent", parent);
  }

}
