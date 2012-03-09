package com.octo.gwt.test.internal.patchers;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.WidgetChangeHandlerManager;
import com.octo.gwt.test.patchers.InitMethod;
import com.octo.gwt.test.patchers.PatchClass;

@PatchClass(Widget.class)
class WidgetPatcher {

  @InitMethod
  static void initClass(CtClass c) throws CannotCompileException,
      NotFoundException {

    // add behavior to Widget.onAttach method
    CtMethod onAttach = c.getMethod("onAttach", "()V");
    onAttach.insertBefore(WidgetChangeHandlerManager.class.getName()
        + ".get().fireOnAttach(this);");

    // add behavior to RadioButton.setName method
    CtMethod onDetach = c.getMethod("onDetach", "()V");
    onDetach.insertBefore(WidgetChangeHandlerManager.class.getName()
        + ".get().fireOnDetach(this);");
  }

}
