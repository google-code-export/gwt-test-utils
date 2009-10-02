package com.octo.gwt.test17.demo.demo2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.octo.gwt.test17.demo.demo2.beans.FooBean;

@RemoteServiceRelativePath("myService")
public interface MyService extends RemoteService {

	public FooBean createBean(String name);

}
