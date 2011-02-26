package com.octo.gwt.test.integration;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("myService")
public class MyServiceImpl implements MyService {

	public MyObject update(MyObject object) {
		object.myField = "updated field by server side code";
		object.myTransientField = "this will not be serialized";

		MyChildObject childObject = new MyChildObject("this is a child !");
		childObject.myChildTransientField = "this will not be serialized too";

		object.myChildObjects.add(childObject);

		return object;
	}

	public void someCallWithException() {
		throw new RuntimeException("Server side thrown exception !!");
	}

}
