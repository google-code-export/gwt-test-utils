package com.octo.gwt.test.demo.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.octo.gwt.test.demo.beans.FooBean;

@RemoteServiceRelativePath("rpc/myService")
public interface MyService extends RemoteService {

  public FooBean createBean(String name);

}
