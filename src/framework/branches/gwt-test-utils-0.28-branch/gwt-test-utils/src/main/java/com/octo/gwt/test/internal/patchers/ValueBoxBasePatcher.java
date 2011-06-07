package com.octo.gwt.test.internal.patchers;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;

@PatchClass(ValueBoxBase.class)
public class ValueBoxBasePatcher extends AutomaticPatcher {

  public void initClass(CtClass c) throws Exception {
    super.initClass(c);

    CtConstructor cons = findConstructor(c, Element.class, Renderer.class,
        Parser.class);
    cons.insertAfter("setText(\"\");");
  }

}
