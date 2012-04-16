package com.googlecode.gwt.test.internal.handlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import com.googlecode.gwt.test.GwtCreateHandler;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

class DefaultGwtCreateHandler implements GwtCreateHandler {

  @SuppressWarnings("unchecked")
  public Object create(Class<?> classLiteral) throws Exception {
    if (classLiteral.isAnnotation() || classLiteral.isArray()
        || classLiteral.isEnum() || classLiteral.isInterface()
        || Modifier.isAbstract(classLiteral.getModifiers())) {
      return null;
    }

    try {
      Constructor<Object> cons = (Constructor<Object>) classLiteral.getDeclaredConstructor();
      return GwtReflectionUtils.instantiateClass(cons);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

}
