package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.Method;

import com.google.gwt.resources.client.ClientBundle;

abstract class ClientBundleCallback {

  private final Class<? extends ClientBundle> wrappedClass;

  protected ClientBundleCallback(Class<? extends ClientBundle> wrappedClass) {
    this.wrappedClass = wrappedClass;
  }

  public abstract Object call(Object proxy, Method method, Object[] args)
      throws Exception;

  public Class<? extends ClientBundle> getWrappedClass() {
    return wrappedClass;
  }
}
