package com.googlecode.gwt.test.internal;

import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static final Logger LOGGER = LoggerFactory.getLogger(ClassSubstituer.class);

  private final Map<String, String> map = new HashMap<String, String>();

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.internal.JavaClassModifier#modify(javassist.CtClass)
   */
  public void modify(CtClass classToModify) {
    LOGGER.debug("Apply 'substitute-class' bytecode modifier");
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
