package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(StackPanel.class)
public class StackPanelPatcher extends AutomaticPatcher {

  @PatchMethod
  public static int findDividerIndex(StackPanel panel,
      com.google.gwt.user.client.Element child) {
    WidgetCollection children = GwtTestReflectionUtils.getPrivateFieldValue(
        panel, "children");

    for (int i = 0; i < children.size(); i++) {
      if (children.get(i).getElement().equals(child)) {
        return i;
      }
    }

    return -1;
  }

}
