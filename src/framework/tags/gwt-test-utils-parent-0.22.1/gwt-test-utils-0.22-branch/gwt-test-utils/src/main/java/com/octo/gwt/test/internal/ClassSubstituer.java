package com.octo.gwt.test.internal;

import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;

class ClassSubstituer implements JavaClassModifier {

  private final Map<String, String> map = new HashMap<String, String>();

  public void modify(CtClass classToModify) {
    for (Map.Entry<String, String> entry : map.entrySet()) {
      if (!classToModify.getName().equals(entry.getKey())) {
        classToModify.getClassFile().renameClass(entry.getKey(),
            entry.getValue());
      }
    }
  }

  public void registerSubstitution(String originalClass,
      String substitutionClass) {
    map.put(originalClass, substitutionClass);
  }

}
