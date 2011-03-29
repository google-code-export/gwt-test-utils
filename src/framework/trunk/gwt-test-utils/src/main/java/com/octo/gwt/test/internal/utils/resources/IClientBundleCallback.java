package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.Method;

import com.google.gwt.resources.client.ClientBundle;

public interface IClientBundleCallback {

  Object call(Object proxy, Method method, Object[] args) throws Exception;

  Class<? extends ClientBundle> getWrappedClass();

}
