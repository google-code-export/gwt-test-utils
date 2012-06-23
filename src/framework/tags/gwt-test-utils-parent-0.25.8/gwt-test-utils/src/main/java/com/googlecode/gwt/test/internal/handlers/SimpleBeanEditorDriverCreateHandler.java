package com.googlecode.gwt.test.internal.handlers;

import java.lang.reflect.Proxy;

import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.googlecode.gwt.test.GwtCreateHandler;
import com.googlecode.gwt.test.internal.editors.SimpleBeanEditorDriverInvocationHandler;

class SimpleBeanEditorDriverCreateHandler implements GwtCreateHandler {

  public Object create(Class<?> classLiteral) throws Exception {
    if (!SimpleBeanEditorDriver.class.isAssignableFrom(classLiteral)) {
      return null;
    }

    return Proxy.newProxyInstance(classLiteral.getClassLoader(),
        new Class[]{classLiteral}, new SimpleBeanEditorDriverInvocationHandler(
            classLiteral));
  }

}
