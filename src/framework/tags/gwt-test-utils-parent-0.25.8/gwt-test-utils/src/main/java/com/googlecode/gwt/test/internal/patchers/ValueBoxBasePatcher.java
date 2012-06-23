package com.googlecode.gwt.test.internal.patchers;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.googlecode.gwt.test.patchers.InitMethod;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.utils.JavassistUtils;

@PatchClass(ValueBoxBase.class)
class ValueBoxBasePatcher {

  @InitMethod
  static void initClass(CtClass c) throws CannotCompileException {
    CtConstructor cons = JavassistUtils.findConstructor(c, Element.class,
        Renderer.class, Parser.class);
    cons.insertAfter("setText(\"\");");
  }

}
