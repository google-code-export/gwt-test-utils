package com.octo.gwt.test.integration;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GwtRpcExceptionHandler {

	void handle(Throwable t, AsyncCallback<?> callback);

}
