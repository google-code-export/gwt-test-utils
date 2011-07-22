package com.octo.gwt.test.internal;

import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;

/**
 * <p>
 * JavaClassModifier which substitute a class by another according to the
 * 'substitute-class' parameters in
 * <code>META-INF/gwt-test-utils.properties</code> file.
 * </p>
 * 
 * <p>
 * <strong>For internal use only.</strong>
 * </p>
 * 
 * @author Gael Lazzari
 * 
 */
class ClassSubstituer implements JavaClassModifier {

  private final Map<String, String> map = new HashMap<String, String>();

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.internal.JavaClassModifier#modify(javassist.CtClass)
   */
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
