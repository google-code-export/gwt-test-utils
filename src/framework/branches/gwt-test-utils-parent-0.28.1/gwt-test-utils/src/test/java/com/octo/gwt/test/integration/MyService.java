package com.octo.gwt.test.integration;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("myService")
public interface MyService extends RemoteService {

  void someCallWithException();

  MyObject update(MyObject object);

}