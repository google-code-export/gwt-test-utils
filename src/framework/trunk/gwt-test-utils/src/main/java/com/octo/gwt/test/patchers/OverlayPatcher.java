package com.octo.gwt.test.patchers;

import java.lang.reflect.Modifier;

import javassist.CtClass;
import javassist.CtMethod;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.internal.patchers.dom.DOMProperties;
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

    String propertyName = getPropertyName(m);
    if (propertyName == null) {
      // this method has not been identified as a property
      return null;
    }

    DOMProperties.get().addDOMProperty(propertyName);

    if (m.getName().startsWith("set")) {
      return PropertyContainerUtils.getCodeSetProperty("this", propertyName,
          "$1");
    } else {
      return "return "
          + PropertyContainerUtils.getCodeGetProperty("this", propertyName,
              m.getReturnType());
    }
  }

  private String getPropertyName(CtMethod m) throws Exception {
    String fieldName = null;
    String name = m.getName();
    if (!CtClass.voidType.equals(m.getReturnType())
        && m.getParameterTypes().length == 0) {
      if (name.startsWith("get")) {
        fieldName = Character.toLowerCase(name.charAt(3)) + name.substring(4);
      } else if (m.getName().startsWith("is")) {
        fieldName = Character.toLowerCase(name.charAt(2)) + name.substring(3);
      } else {
        fieldName = name;
      }
    } else if (name.startsWith("set") && m.getParameterTypes().length == 1) {
      fieldName = Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    return fieldName;
  }
}
