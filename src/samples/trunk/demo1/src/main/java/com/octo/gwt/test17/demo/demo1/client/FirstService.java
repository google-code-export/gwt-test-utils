package com.octo.gwt.test17.demo.demo1.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.octo.gwt.test17.demo.demo1.beans.FooBean;

@RemoteServiceRelativePath("firstService")
public interface FirstService extends RemoteService {

	public FooBean createBean(String name);
	
}
