package com.octo.gwt.test.internal.modifiers;

import javassist.CtClass;

public class ClassSubstituer implements JavaClassModifier {

  private String substitutionClass;
  private String originalClass;

  public ClassSubstituer(String originalClass, String substitionClass) {
    this.originalClass = originalClass;
    this.substitutionClass = substitionClass;
  }

  public void modify(CtClass classToModify) {
    if (classToModify.getName().equals(originalClass)) {
      return;
    }

    classToModify.getClassFile().renameClass(originalClass, substitutionClass);
  }

}
