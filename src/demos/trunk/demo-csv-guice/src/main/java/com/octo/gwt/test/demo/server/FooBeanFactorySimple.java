package com.octo.gwt.test.demo.server;

import com.octo.gwt.test.demo.beans.FooBean;

public class FooBeanFactorySimple implements FooBeanFactory {

  public FooBean createFooBean(String name) {
    FooBean fooBean = new FooBean();
    fooBean.setName(name);

    return fooBean;
  }

}
