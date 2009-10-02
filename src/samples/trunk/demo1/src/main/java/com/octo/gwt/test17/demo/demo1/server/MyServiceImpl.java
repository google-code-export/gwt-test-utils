package com.octo.gwt.test17.demo.demo1.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.octo.gwt.test17.demo.demo1.beans.FooBean;
import com.octo.gwt.test17.demo.demo1.client.MyService;

public class MyServiceImpl extends RemoteServiceServlet implements MyService {

	private static final long serialVersionUID = -285469868016964214L;

	public FooBean createBean(String name) {
		FooBean fb = new FooBean();
		fb.setName(name);

		return fb;
	}

}
