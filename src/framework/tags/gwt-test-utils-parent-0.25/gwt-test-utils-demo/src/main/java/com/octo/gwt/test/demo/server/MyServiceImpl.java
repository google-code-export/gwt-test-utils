package com.octo.gwt.test.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.octo.gwt.test.demo.beans.FooBean;
import com.octo.gwt.test.demo.client.MyService;

public class MyServiceImpl extends RemoteServiceServlet implements MyService {

	private static final long serialVersionUID = -285469868016964214L;

	private static final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

	public FooBean createBean(String name) {
		FooBean fb = new FooBean();
		fb.setName(name);

		logger.info(FooBean.class.getSimpleName() + " instance create with '" + name + "'");

		return fb;
	}

}
