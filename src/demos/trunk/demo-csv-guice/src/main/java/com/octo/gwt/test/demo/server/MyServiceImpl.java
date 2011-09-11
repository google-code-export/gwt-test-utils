package com.octo.gwt.test.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.octo.gwt.test.demo.beans.FooBean;
import com.octo.gwt.test.demo.client.MyService;

@Singleton
public class MyServiceImpl extends RemoteServiceServlet implements MyService {

  private static final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

  private static final long serialVersionUID = -285469868016964214L;

  private final FooBeanFactory fooBeanFactory;

  @Inject
  public MyServiceImpl(FooBeanFactory fooBeanFactory) {
    this.fooBeanFactory = fooBeanFactory;
  }

  public FooBean createBean(String name) {
    FooBean fb = fooBeanFactory.createFooBean(name);

    logger.info(FooBean.class.getSimpleName() + " instance create with '"
        + name + "'");

    return fb;
  }

}
