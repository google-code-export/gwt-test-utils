package com.octo.gwt.test.integration;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("myService")
public interface MyService extends RemoteService {

	MyObject update(MyObject object);

	void someCallWithException();

}