package com.octo.gwt.test.csv.tools.integ;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("myService")
public interface MyRemoteService extends RemoteService {

	String myMethod(String param1);

	MyCustomObject myMethod2(MyCustomObject object);

	void myMethod3();

}