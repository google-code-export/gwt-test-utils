package com.octo.gwt.test.integ.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IGwtRpcExceptionHandler {

  void handle(Throwable t, AsyncCallback<?> callback);

}
