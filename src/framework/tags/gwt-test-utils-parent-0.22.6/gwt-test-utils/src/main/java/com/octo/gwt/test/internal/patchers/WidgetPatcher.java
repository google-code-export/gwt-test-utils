package com.octo.gwt.test.internal.patchers;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.finder.GwtFinder;
import com.octo.gwt.test.patchers.InitMethod;
import com.octo.gwt.test.patchers.PatchClass;

@PatchClass(Widget.class)
class WidgetPatcher {

  @InitMethod
  static void initClass(CtClass c) throws CannotCompileException,
      NotFoundException {

    // add behavior to Widget.onAttach method
    CtMethod onAttach = c.getMethod("onAttach", "()V");
    onAttach.insertBefore(GwtFinder.class.getName() + ".onAttach(this);");

    // add behavior to RadioButton.setName method
    CtMethod onDetach = c.getMethod("onDetach", "()V");
    onDetach.insertBefore(GwtFinder.class.getName() + ".onDetach(this);");
  }

}
