package com.octo.gwt.test.internal.patchers.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patchers.InitMethod;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.JavassistUtils;

@PatchClass(SelectElement.class)
public class SelectElementPatcher {

  private static final String SELECTED_INDEX_FIELD = "SelectedIndex";

  @PatchMethod
  public static int getSize(SelectElement select) {
    int size = 0;

    for (int i = 0; i < select.getChildNodes().getLength(); i++) {
      Element e = select.getChildNodes().getItem(i).cast();
      if (UIObject.isVisible(e)) {
        size++;
      }
    }

    return size;
  }

  @InitMethod
  static void initClass(CtClass c) throws Exception {
    CtConstructor cons = JavassistUtils.findConstructor(c);

    cons.insertAfter(PropertyContainerHelper.getCodeSetProperty("this",
        SELECTED_INDEX_FIELD, "-1") + ";");
  }

}
