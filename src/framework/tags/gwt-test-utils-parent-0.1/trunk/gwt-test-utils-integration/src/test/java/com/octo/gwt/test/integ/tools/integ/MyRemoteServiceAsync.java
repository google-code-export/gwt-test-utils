package com.octo.gwt.test.integ.tools.integ;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyRemoteServiceAsync {

	void myMethod(String param1, AsyncCallback<String> callback);

}
