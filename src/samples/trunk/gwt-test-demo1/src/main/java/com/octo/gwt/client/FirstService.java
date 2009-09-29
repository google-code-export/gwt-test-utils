package com.octo.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.octo.gwt.beans.FooBean;

@RemoteServiceRelativePath("firstService")
public interface FirstService extends RemoteService {

	public FooBean createBean(String name);
	
}
