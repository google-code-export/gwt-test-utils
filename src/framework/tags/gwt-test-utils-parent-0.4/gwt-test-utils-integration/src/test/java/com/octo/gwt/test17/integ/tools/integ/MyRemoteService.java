package com.octo.gwt.test17.integ.tools.integ;

import com.google.gwt.user.client.rpc.RemoteService;

public interface MyRemoteService extends RemoteService {

	String myMethod(String param1);

}