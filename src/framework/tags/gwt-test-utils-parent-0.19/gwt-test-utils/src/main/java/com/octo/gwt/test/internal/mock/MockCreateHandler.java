package com.octo.gwt.test.internal.mock;

import java.lang.reflect.Method;

import com.octo.gwt.test.GwtCreateHandler;

public interface MockCreateHandler extends GwtCreateHandler {

	public void beforeTest();

	public void afterTest();

	public void replay();

	public void verify();

	public void reset();

	public Object addMockedObject(Class<?> createClass, Object mock);

	public void expectServiceAndCallbackOnFailure(final Throwable exception);

	public <T> void expectServiceAndCallbackOnSuccess(final T object);

	public <T> T createMockAndKeepOneMethod(Class<T> clazz, String methodName);

	public <T> T createMockAndKeepMethods(Class<T> clazz, final boolean keepSetters, final Method... list);

}
