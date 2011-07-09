package com.octo.gwt.test.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.gwt.test.demo.beans.FooBean;
import com.octo.gwt.test.demo.client.MyService;

public class MyServiceImpl implements MyService {

  private static final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

  public FooBean createBean(String name) {
    FooBean fb = new FooBean();
    fb.setName(name);

    logger.info(FooBean.class.getSimpleName() + " instance create with '"
        + name + "'");

    return fb;
  }

}
