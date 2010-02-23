package com.octo.gwt.test.integ.handler;

import java.lang.reflect.InvocationTargetException;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IGwtRpcExceptionHandler {

	void handle(InvocationTargetException invocationTargetException, AsyncCallback<?> callback);

}
