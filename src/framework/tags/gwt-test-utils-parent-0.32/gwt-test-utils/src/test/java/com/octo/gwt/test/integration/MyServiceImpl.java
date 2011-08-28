package com.octo.gwt.test.integration;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.octo.gwt.test.integration.client.MyChildObject;
import com.octo.gwt.test.integration.client.MyObject;

@RemoteServiceRelativePath("myService")
public class MyServiceImpl implements MyService {

  public void someCallWithException() {
    throw new RuntimeException("Server side thrown exception !!");
  }

  public MyObject update(MyObject object) {
    object.setMyField("updated field by server side code");
    object.setMyTransientField("this will not be serialized");

    MyChildObject childObject = new MyChildObject("this is a child !");
    childObject.setMyChildTransientField("this will not be serialized too");
    childObject.setMyField("the field inherited from the parent has been updated !");
    childObject.setMyTransientField("this field is not expected to be serialized too");

    object.getMyChildObjects().add(childObject);

    return object;
  }

}
