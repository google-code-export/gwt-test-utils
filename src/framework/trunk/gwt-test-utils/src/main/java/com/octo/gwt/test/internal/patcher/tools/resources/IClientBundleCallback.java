package com.octo.gwt.test.internal.patcher.tools.resources;

import java.lang.reflect.Method;

import com.google.gwt.resources.client.ClientBundle;

public interface IClientBundleCallback {

	Class<? extends ClientBundle> getWrappedClass();

	Object call(Object proxy, Method method, Object[] args) throws Exception;

}
