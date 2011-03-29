package com.octo.gwt.test.internal;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class GwtClassPool {

  private static ClassPool classPool = ClassPool.getDefault();

  public static ClassPool get() {
    return classPool;
  }

  public static CtClass getCtClass(Class<?> clazz) {
    try {
      return classPool.get(clazz.getName());
    } catch (NotFoundException e) {
      throw new RuntimeException("Cannot find class '" + clazz.getName() + "'");
    }
  }
}
