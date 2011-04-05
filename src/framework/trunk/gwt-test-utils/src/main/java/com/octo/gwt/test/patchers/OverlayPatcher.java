package com.octo.gwt.test.patchers;

import java.lang.reflect.Modifier;

import javassist.CtClass;
import javassist.CtMethod;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;

/**
 * 
 * Base Patcher to use for all {@link JavaScriptObject} subclasses.
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public class OverlayPatcher extends AutomaticPatcher {

  public String getNewBody(CtMethod m) throws Exception {
    String superNewBody = super.getNewBody(m);
    if (superNewBody != null || !Modifier.isNative(m.getModifiers())) {
      return superNewBody;
    }

    String fieldName = getPropertyName(m);
    if (fieldName == null) {
      // this method has not been identified as a property
      return null;
    }

    if (m.getName().startsWith("set")) {
      return PropertyContainerUtils.getCodeSetProperty("this", fieldName, "$1");
    } else {
      return "return "
          + PropertyContainerUtils.getCodeGetProperty("this", fieldName,
              m.getReturnType());
    }
  }

  private String getPropertyName(CtMethod m) throws Exception {
    String fieldName = null;
    if (!CtClass.voidType.equals(m.getReturnType())
        && m.getParameterTypes().length == 0) {
      if (m.getName().startsWith("get")) {
        fieldName = m.getName().substring(3);
      } else if (m.getName().startsWith("is")) {
        fieldName = m.getName().substring(2);
      } else {
        fieldName = m.getName();
      }
    } else if (m.getName().startsWith("set")
        && m.getParameterTypes().length == 1) {
      fieldName = m.getName().substring(3);
    }

    return fieldName;
  }
}
