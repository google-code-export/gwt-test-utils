package com.octo.gwt.test.internal.patchers;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.user.client.ui.PopupPanel;
import com.octo.gwt.test.patchers.InitMethod;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.utils.JavassistUtils;

@PatchClass(PopupPanel.class)
public class PopupPanelPatcher {

  @InitMethod
  public static void initClass(CtClass c) throws CannotCompileException {
    CtConstructor cons = JavassistUtils.findConstructor(c);

    cons.insertAfter("getElement().getStyle().setProperty(\"visibility\", \"hidden\");");
  }

}
