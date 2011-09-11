package com.octo.gwt.test.demo.server;

import org.springframework.stereotype.Service;

import com.octo.gwt.test.demo.beans.FooBean;

@Service
public class FooBeanFactorySimple implements FooBeanFactory {

  public FooBean createFooBean(String name) {
    FooBean fooBean = new FooBean();
    fooBean.setName(name);

    return fooBean;
  }

}
