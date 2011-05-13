package com.octo.gwt.test.patchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.octo.gwt.test.Patcher;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.utils.GwtPatcherUtils;
import com.octo.gwt.test.patchers.PatchMethod.Type;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * <p>
 * The base class for implementing {@link Patcher} methods. It relies on the
 * convenient annotation {@link PatchMethod} which marks a method to be a
 * substitution for another one following a simple convention (which can be
 * overriden by configuration).
 * </p>
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public class AutomaticPatcher implements Patcher {

  public static final String INSERT_AFTER = "INSERT_AFTER";
  public static final String INSERT_BEFORE = "INSERT_BEFORE";

  private Map<Method, PatchMethod> annotatedMethods;

  private CtClass c;

  private List<Method> processedMethods;

  public void finalizeClass(CtClass c) throws Exception {
    for (Method m : annotatedMethods.keySet()) {
      if (!processedMethods.contains(m)) {
        throw new GwtTestPatchException("@" + PatchMethod.class.getSimpleName()
            + " not used : " + m);
      }
    }

  }

  public String getNewBody(CtMethod m) throws Exception {
    String newBody = null;
    Entry<Method, PatchMethod> e = findAnnotatedMethod(m);
    if (e != null) {
      Method annotatedMethod = e.getKey();
      if (!Modifier.isStatic(annotatedMethod.getModifiers())) {
        throw new GwtTestPatchException("@" + PatchMethod.class.getSimpleName()
            + " " + annotatedMethod + " has to be static");
      }

      switch (e.getValue().type()) {
        case INSERT_CODE_BEFORE:
          newBody = INSERT_BEFORE + treatMethod(annotatedMethod);
          break;
        case INSERT_CODE_AFTER:
          newBody = INSERT_AFTER + treatMethod(annotatedMethod);
          break;
        case NEW_CODE_AS_STRING:
          newBody = treatMethod(annotatedMethod);
          break;
        case STATIC_CALL:
          processedMethods.add(annotatedMethod);
          StringBuilder buffer = new StringBuilder();
          buffer.append("{");
          buffer.append("return ");
          buffer.append(this.getClass().getName() + "."
              + annotatedMethod.getName());
          buffer.append("(");
          boolean append = false;
          if (!Modifier.isStatic(m.getModifiers())) {
            buffer.append("this");
            append = true;
          }
          for (int i = 0; i < m.getParameterTypes().length; i++) {
            if (append) {
              buffer.append(", ");
            }
            int j = i + 1;
            buffer.append("$" + j);
            append = true;
          }
          buffer.append(");");
          buffer.append("}");

          newBody = buffer.toString();
          break;
      }
    }

    return newBody;
  }

  public void initClass(CtClass c) throws Exception {
    this.c = c;
    this.annotatedMethods = GwtReflectionUtils.getAnnotatedMethod(
        this.getClass(), PatchMethod.class);
    this.processedMethods = new ArrayList<Method>();
  }

  private Entry<Method, PatchMethod> findAnnotatedMethod(CtMethod m)
      throws Exception {
    for (Entry<Method, PatchMethod> entry : annotatedMethods.entrySet()) {
      String methodName = entry.getKey().getName();
      if (entry.getValue().value().length() > 0) {
        methodName = entry.getValue().value();
      }
      if (m.getName().equals(methodName)) {
        if (entry.getValue().args().length == 1
            && entry.getValue().args()[0] == PatchMethod.class
            // Either this is a new method or it has to have a compatible
            // signature
            && (entry.getValue().type() != Type.STATIC_CALL || hasCompatibleSignature(
                m, entry.getKey()))) {
          return entry;
        } else {
          if (hasSameSignature(m, entry.getValue().args(),
              m.getParameterTypes())) {
            return entry;
          }
        }
      }
    }
    return null;
  }

  private boolean hasCompatibleSignature(CtMethod methodFound,
      Method methodAsked) throws Exception {
    CtClass[] classesFound = methodFound.getParameterTypes();
    Class<?>[] classesAsked = methodAsked.getParameterTypes();

    boolean compat = hasSameSignature(methodFound, classesAsked, classesFound);

    // account for the case where the method is non static in the original class
    // and we need to pass the object into the static patching method
    if (!compat && classesAsked.length >= 1
        && inheritsFrom(classesAsked[0], methodFound.getDeclaringClass())) {
      Class<?>[] classesWithoutThis = new Class[classesAsked.length - 1];
      for (int i = 1; i < classesAsked.length; i++) {
        classesWithoutThis[i - 1] = classesAsked[i];
      }

      compat = hasSameSignature(methodFound, classesWithoutThis, classesFound);
    }

    return compat;
  }

  private boolean hasSameSignature(CtMethod m, Class<?>[] classesAsked,
      CtClass[] classesFound) throws Exception {
    if (classesAsked.length != classesFound.length) {
      return false;
    }
    for (int i = 0; i < classesAsked.length; i++) {
      CtClass classFound = classesFound[i];
      if (!classFound.getName().equals(classesAsked[i].getName())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks to see if the checkCls inherits in some way from the superClass.
   * i.e. does checkCls implement or extend superClass.
   * 
   * @param checkCls
   * @param superClass
   * @return
   * @throws NotFoundException
   */
  private boolean inheritsFrom(Class<?> checkCls, CtClass superClass)
      throws NotFoundException {
    List<CtClass> checked = new ArrayList<CtClass>();

    return inheritsFrom(checkCls, superClass, checked);
  }

  private boolean inheritsFrom(Class<?> checkCls, CtClass superClass,
      List<CtClass> checked) throws NotFoundException {
    if (checked.contains(superClass)) {
      return false;
    }

    checked.add(superClass);
    boolean inherits = checkCls.getName().equals(superClass.getName());

    if (!inherits) {
      for (CtClass intf : superClass.getInterfaces()) {
        inherits = inheritsFrom(checkCls, intf);
      }
    }

    if (!inherits && superClass.getSuperclass() != null) {
      inherits = inheritsFrom(checkCls, superClass.getSuperclass(), checked);
    }

    return inherits;
  }

  private String treatMethod(Method annotatedMethod)
      throws IllegalAccessException, InvocationTargetException {
    processedMethods.add(annotatedMethod);
    List<Object> params = new ArrayList<Object>();
    for (Class<?> clazz : annotatedMethod.getParameterTypes()) {
      if (clazz == CtClass.class) {
        params.add(c);
      } else {
        throw new GwtTestPatchException("Not managed param " + clazz
            + " for method " + annotatedMethod);
      }
    }
    if (annotatedMethod.getReturnType() != String.class) {
      throw new GwtTestPatchException("Wrong return type "
          + annotatedMethod.getReturnType() + " for method " + annotatedMethod);
    }
    return (String) annotatedMethod.invoke(null, params.toArray());
  }

  protected CtConstructor findConstructor(CtClass ctClass,
      Class<?>... argsClasses) throws NotFoundException {
    return GwtPatcherUtils.findConstructor(ctClass, argsClasses);
  }

}
