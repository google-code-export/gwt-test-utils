package com.octo.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.octo.gwt.beans.FooBean;
import com.octo.gwt.client.FirstService;

public class FirstServiceImpl extends RemoteServiceServlet implements FirstService {

	private static final long serialVersionUID = -285469868016964214L;

	public FooBean createBean(String name) {
		FooBean fb = new FooBean();
		fb.setName(name);

		return fb;
	}

}
