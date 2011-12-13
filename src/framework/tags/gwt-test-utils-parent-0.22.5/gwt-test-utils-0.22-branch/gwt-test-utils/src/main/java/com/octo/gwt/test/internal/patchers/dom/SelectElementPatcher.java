package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SelectElement;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(SelectElement.class)
class SelectElementPatcher {

  @PatchMethod
  static int getSize(SelectElement select) {
    int visibleSize = JavaScriptObjects.getInteger(select,
        JsoProperties.SELECTED_SIZE);
    int actualSize = select.getChildNodes().getLength();

    if (visibleSize == -1 || visibleSize > actualSize) {
      visibleSize = actualSize;
    }

    return visibleSize;
  }

  static void refreshSelect(SelectElement select) {
    int visibleSize = select.getSize();

    for (int i = 0; i < select.getChildNodes().getLength(); i++) {
      Element e = select.getChildNodes().getItem(i).cast();

      if (i < visibleSize) {
        // this element is visible
        e.getStyle().clearProperty("display");
      } else {
        // it's not visible
        e.getStyle().setProperty("display", "none");
      }
    }

  }

  @PatchMethod
  static void setSize(SelectElement select, int size) {
    JavaScriptObjects.setProperty(select, JsoProperties.SELECTED_SIZE, size);
    refreshSelect(select);
  }

}
