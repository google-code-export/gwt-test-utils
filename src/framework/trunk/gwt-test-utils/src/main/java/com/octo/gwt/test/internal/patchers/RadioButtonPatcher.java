package com.octo.gwt.test.internal.patchers;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

import com.google.gwt.user.client.ui.RadioButton;
import com.octo.gwt.test.internal.utils.RadioButtonManager;
import com.octo.gwt.test.patchers.InitMethod;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.JavassistUtils;

@PatchClass(RadioButton.class)
class RadioButtonPatcher {

  @InitMethod
  static void initClass(CtClass c) throws CannotCompileException {
    CtConstructor cons = JavassistUtils.findConstructor(c, String.class);

    cons.insertAfter(RadioButtonManager.class.getName()
        + ".onInstanciation(this);");

    CtMethod setValueMethod = CtMethod.make(
        "public void setValue(Boolean value, boolean fireEvents) { super.setValue($1, $2); "
            + RadioButtonManager.class.getName()
            + ".onRadioGroupChanged(this, $1, $2); }", c);
    c.addMethod(setValueMethod);
  }

  @PatchMethod
  static void setName(RadioButton rb, String name) {
    RadioButtonManager.onSetName(rb, name);
  }

}
