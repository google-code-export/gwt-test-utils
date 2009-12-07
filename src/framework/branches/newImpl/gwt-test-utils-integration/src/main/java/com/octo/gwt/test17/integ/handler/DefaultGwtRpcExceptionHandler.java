package com.octo.gwt.test17.integ.handler;

import java.lang.reflect.InvocationTargetException;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DefaultGwtRpcExceptionHandler implements IGwtRpcExceptionHandler {

	public void handle(InvocationTargetException invocationTargetException, AsyncCallback<?> callback) {
		callback.onFailure(invocationTargetException.getCause());
	}

}
