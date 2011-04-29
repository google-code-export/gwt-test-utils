package com.octo.gwt.test.internal.utils;

import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import com.octo.gwt.test.Patcher;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.patchers.AutomaticPatcher;

/**
 * 
 * Some patching utility methods. Bytecode manipulation relies on javassist API.
 * <strong>For internal use only.</strong>
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public class GwtPatcherUtils {

  public static boolean areAssertionEnabled() {
    boolean enabled = false;
    assert enabled = true;
    return enabled;
  }

  public static CtConstructor findConstructor(CtClass ctClass,
      Class<?>... argsClasses) throws NotFoundException {
    List<CtConstructor> l = new ArrayList<CtConstructor>();

    for (CtConstructor c : ctClass.getDeclaredConstructors()) {
      if (argsClasses == null
          || argsClasses.length == c.getParameterTypes().length) {
        l.add(c);

        if (argsClasses != null) {
          int i = 0;
          for (Class<?> argClass : argsClasses) {
            if (!argClass.getName().equals(c.getParameterTypes()[i].getName())) {
              l.remove(c);
              continue;
            }
            i++;
          }
        }
      }
    }

    if (l.size() == 1) {
      return l.get(0);
    }
    if (l.size() == 0) {
      throw new GwtTestPatchException(
          "Unable to find a constructor with the specifed parameter types in class "
              + ctClass.getName());
    }
    throw new GwtTestPatchException("Multiple constructor in class "
        + ctClass.getName()
        + ", you have to set parameter types discriminators");
  }

  public static void patch(CtClass c, Patcher patcher) throws Exception {
    treatClassToPatch(c);

    if (patcher != null) {
      patcher.initClass(c);
    }

    for (CtMethod m : c.getDeclaredMethods()) {
      boolean wasAbstract = false;
      String newBody = null;
      if (Modifier.isAbstract(m.getModifiers())) {
        m.setModifiers(m.getModifiers() - Modifier.ABSTRACT);
        wasAbstract = true;
      }
      if (patcher != null) {
        newBody = patcher.getNewBody(m);
      }
      if (newBody != null) {
        if (newBody.startsWith(AutomaticPatcher.INSERT_BEFORE)) {
          insertBefore(m,
              newBody.substring(AutomaticPatcher.INSERT_BEFORE.length()));
        } else if (newBody.startsWith(AutomaticPatcher.INSERT_AFTER)) {
          insertAfter(m,
              newBody.substring(AutomaticPatcher.INSERT_AFTER.length()));
        } else {
          replaceImplementation(m, newBody);
        }
      } else if (wasAbstract) {
        if (patcher != null) {
          m.setBody("{ throw new "
              + UnsupportedOperationException.class.getName()
              + "(\"Abstract method '" + c.getSimpleName() + "." + m.getName()
              + "()' is not patched by " + patcher.getClass().getName()
              + "\"); }");
        } else {
          m.setBody("{ throw new "
              + UnsupportedOperationException.class.getName()
              + "(\"Abstract method '" + c.getSimpleName() + "." + m.getName()
              + "()' is not patched by any declared "
              + Patcher.class.getSimpleName() + " instance\"); }");
        }
      }
    }

    if (patcher != null) {
      patcher.finalizeClass(c);
    }
  }

  private static void addDefaultConstructor(CtClass c)
      throws CannotCompileException {
    CtConstructor cons;
    try {
      cons = c.getDeclaredConstructor(new CtClass[]{});
      cons.setModifiers(Modifier.PUBLIC);
    } catch (NotFoundException e) {
      cons = new CtConstructor(new CtClass[]{}, c);
      cons.setBody(";");
      c.addConstructor(cons);
    }

  }

  private static void insertAfter(CtMethod m, String newBody) throws Exception {
    removeNativeModifier(m);
    m.insertAfter(newBody);
  }

  private static void insertBefore(CtMethod m, String newBody) throws Exception {
    removeNativeModifier(m);
    m.insertBefore(newBody);
  }

  private static void removeNativeModifier(CtMethod m) throws Exception {
    if (Modifier.isNative(m.getModifiers())) {
      m.setModifiers(m.getModifiers() - Modifier.NATIVE);
    }
  }

  private static void replaceImplementation(CtMethod m, String src)
      throws Exception {
    removeNativeModifier(m);

    if (src == null || src.trim().length() == 0) {
      m.setBody(null);
    } else {
      src = src.trim();
      if (!src.startsWith("{")) {
        if (!m.getReturnType().equals(CtClass.voidType)
            && !src.startsWith("return")) {
          src = "{ return ($r)($w) " + src;
        } else {
          src = "{ " + src;
        }
      }

      if (!src.endsWith("}")) {
        if (!src.endsWith(";")) {
          src = src + "; }";
        } else {
          src = src + " }";
        }
      }
      try {
        m.setBody(src);
      } catch (CannotCompileException e) {
        throw new GwtTestPatchException("Unable to compile body " + src, e);
      }
    }
  }

  private static void treatClassToPatch(CtClass c)
      throws CannotCompileException {
    if (c == null) {
      throw new IllegalArgumentException("the class to patch cannot be null");
    }

    int modifiers = c.getModifiers();

    if (Modifier.isAnnotation(modifiers)) {
      throw new IllegalArgumentException(
          "the class to patch cannot be an annotation");
    }
    if (Modifier.isInterface(modifiers)) {
      throw new IllegalArgumentException(
          "the class to patch cannot be an interface");
    }
    if (Modifier.isEnum(modifiers)) {
      throw new IllegalArgumentException("the class to patch cannot be an enum");
    }

    c.setModifiers(Modifier.PUBLIC);

    addDefaultConstructor(c);
  }

  private GwtPatcherUtils() {

  }

}
