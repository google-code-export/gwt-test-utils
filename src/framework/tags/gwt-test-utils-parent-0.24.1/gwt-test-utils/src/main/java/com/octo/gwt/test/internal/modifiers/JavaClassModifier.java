package com.octo.gwt.test.internal.modifiers;

import javassist.CtClass;

public interface JavaClassModifier {

  public void modify(CtClass classToModify) throws Exception;

}
