package com.octo.gwt.test.integration;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A basic {@link GwtRpcExceptionHandler} which just call the
 * {@link AsyncCallback#onFailure(Throwable)} method of the callback instance.
 * 
 * @author Bertrand Paquet
 * 
 */
public class DefaultGwtRpcExceptionHandler implements GwtRpcExceptionHandler {

  public void handle(Throwable t, AsyncCallback<?> callback) {
    callback.onFailure(t);
  }

}
