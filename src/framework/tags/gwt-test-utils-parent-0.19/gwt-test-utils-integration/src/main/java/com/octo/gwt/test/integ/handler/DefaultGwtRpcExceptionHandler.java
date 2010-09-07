package com.octo.gwt.test.integ.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DefaultGwtRpcExceptionHandler implements IGwtRpcExceptionHandler {

	public void handle(Throwable t, AsyncCallback<?> callback) {
		callback.onFailure(t);
	}

}
