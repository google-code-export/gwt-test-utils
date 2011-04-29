package com.octo.gwt.test.patchers;

import java.lang.reflect.Modifier;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.GwtClassPool;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;

/**
 * 
 * Base Patcher to use for all {@link JavaScriptObject} subclasses.
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public class OverlayPatcher extends AutomaticPatcher {

  private static CtClass STRING_TYPE;

  static {

    try {
      STRING_TYPE = GwtClassPool.get().get(String.class.getName());
    } catch (NotFoundException e) {
      // Never append
      throw new GwtTestPatchException(e);
    }
  }

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

    JsoProperties.get().addJsoProperty(propertyName);

    if (m.getName().startsWith("set")) {
      return getCodeSetProperty("this", propertyName, "$1");
    } else {
      return "return "
          + getCodeGetProperty("this", propertyName, m.getReturnType());
    }
  }

  private String getCodeGetProperty(String object, String fieldName,
      CtClass returnType) {
    if (returnType == STRING_TYPE) {
      return JavaScriptObjects.class.getName() + ".getString(" + object
          + ", \"" + fieldName + "\")";
    } else if (returnType == CtClass.booleanType) {
      return JavaScriptObjects.class.getName() + ".getBoolean(" + object
          + ", \"" + fieldName + "\")";
    } else if (returnType == CtClass.intType) {
      return JavaScriptObjects.class.getName() + ".getInteger(" + object
          + ", \"" + fieldName + "\")";
    } else if (returnType == CtClass.doubleType) {
      return JavaScriptObjects.class.getName() + ".getDouble(" + object
          + ", \"" + fieldName + "\")";
    } else if (returnType == CtClass.shortType) {
      return JavaScriptObjects.class.getName() + ".getShort(" + object + ", \""
          + fieldName + "\")";
    }
    return "(" + returnType.getName() + ") "
        + JavaScriptObjects.class.getName() + ".getObject(" + object + ", \""
        + fieldName + "\")";
  }

  private String getCodeSetProperty(String object, String propertyName,
      String propertyValue) {
    return JavaScriptObjects.class.getName() + ".setProperty(" + object
        + ", \"" + propertyName + "\", " + propertyValue + ")";
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
