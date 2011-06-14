package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.widget.Component;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(Component.class)
public class ComponentPatcher {

  @PatchMethod
  public static void setParent(Component component, Widget parent) {
    GwtReflectionUtils.setPrivateFieldValue(component, "parent", parent);
  }

}
