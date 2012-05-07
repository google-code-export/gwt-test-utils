package com.googlecode.gwt.test.spring;

import com.googlecode.gwt.test.internal.runner.AbstractGwtRunner;
import com.googlecode.gwt.test.internal.runner.AbstractGwtRunnerFactory;

public class GwtSpringRunner extends AbstractGwtRunner {

  public GwtSpringRunner(Class<?> clazz) throws Throwable {
    super(clazz);
  }

  @Override
  protected AbstractGwtRunnerFactory getRunnerFactory() {
    return new GwtSpringRunnerFactory();
  }

}