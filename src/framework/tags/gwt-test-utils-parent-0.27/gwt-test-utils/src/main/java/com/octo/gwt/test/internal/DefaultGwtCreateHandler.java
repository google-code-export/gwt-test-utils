package com.octo.gwt.test.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class DefaultGwtCreateHandler implements GwtCreateHandler {

  @SuppressWarnings("unchecked")
  public Object create(Class<?> classLiteral) throws Exception {
    if (classLiteral.isAnnotation() || classLiteral.isArray()
        || classLiteral.isEnum() || classLiteral.isInterface()
        || Modifier.isAbstract(classLiteral.getModifiers())) {
      return null;
    }

    try {
      Constructor<Object> cons = (Constructor<Object>) classLiteral.getDeclaredConstructor();
      GwtReflectionUtils.makeAccessible(cons);
      return GwtReflectionUtils.instantiateClass(cons);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

}
