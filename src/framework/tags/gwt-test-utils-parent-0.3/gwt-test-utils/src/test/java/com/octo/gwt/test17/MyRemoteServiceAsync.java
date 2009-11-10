package com.octo.gwt.test17;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyRemoteServiceAsync {

	void myMethod(String param1, AsyncCallback<String> callback);
	
}
