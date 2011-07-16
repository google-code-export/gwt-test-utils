package com.octo.gwt.test.spring;

import com.octo.gwt.test.internal.runner.AbstractGwtRunner;
import com.octo.gwt.test.internal.runner.AbstractGwtRunnerFactory;

public class GwtSpringRunner extends AbstractGwtRunner {

  public GwtSpringRunner(Class<?> clazz) throws Exception {
    super(clazz);
  }

  @Override
  protected AbstractGwtRunnerFactory getRunnerFactory() {
    return new GwtSpringRunnerFactory();
  }

}