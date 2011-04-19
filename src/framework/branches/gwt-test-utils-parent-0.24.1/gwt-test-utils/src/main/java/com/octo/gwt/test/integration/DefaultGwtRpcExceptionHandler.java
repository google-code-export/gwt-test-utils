package com.octo.gwt.test.integration;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DefaultGwtRpcExceptionHandler implements GwtRpcExceptionHandler {

	public void handle(Throwable t, AsyncCallback<?> callback) {
		callback.onFailure(t);
	}

}
