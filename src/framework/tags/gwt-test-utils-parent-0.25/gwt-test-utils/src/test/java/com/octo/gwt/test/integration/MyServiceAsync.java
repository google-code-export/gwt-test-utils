package com.octo.gwt.test.integration;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyServiceAsync {

	void update(MyObject object, AsyncCallback<MyObject> callback);

	void someCallWithException(AsyncCallback<Void> callback);

}
