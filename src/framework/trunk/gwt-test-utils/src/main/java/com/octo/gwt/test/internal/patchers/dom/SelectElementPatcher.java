package com.octo.gwt.test.internal.patchers.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(SelectElement.class)
public class SelectElementPatcher extends OverlayPatcher {

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

  @Override
  public void initClass(CtClass c) throws Exception {
    super.initClass(c);
    CtConstructor cons = findConstructor(c);

    cons.insertAfter(PropertyContainerUtils.getCodeSetProperty("this",
        DOMProperties.SELECTED_INDEX_FIELD, "-1") + ";");
  }

}
