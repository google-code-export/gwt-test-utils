package com.octo.gwt.test.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.gwt.test.demo.beans.FooBean;
import com.octo.gwt.test.demo.client.MyService;

@Service
public class MyServiceImpl implements MyService {

  private static final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

  @Autowired
  private FooBeanFactory fooBeanFactory;

  public FooBean createBean(String name) {
    FooBean fb = fooBeanFactory.createFooBean(name);

    logger.info(FooBean.class.getSimpleName() + " instance create with '"
        + name + "'");

    return fb;
  }

}
