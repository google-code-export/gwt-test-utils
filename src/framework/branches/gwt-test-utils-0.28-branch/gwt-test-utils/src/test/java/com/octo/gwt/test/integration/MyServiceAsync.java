package com.octo.gwt.test.integration;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.integration.client.MyObject;

public interface MyServiceAsync {

  void someCallWithException(AsyncCallback<Void> callback);

  void update(MyObject object, AsyncCallback<MyObject> callback);

}
