package com.googlecode.gwt.test.spring;

import com.googlecode.gwt.test.internal.runner.AbstractGwtRunnerFactory;

class GwtSpringRunnerFactory extends AbstractGwtRunnerFactory {

  @Override
  protected String getRunnerClassName(boolean hasJUnit45OrHigher) {
    return "org.springframework.test.context.junit4.SpringJUnit4ClassRunner";
  }

}
