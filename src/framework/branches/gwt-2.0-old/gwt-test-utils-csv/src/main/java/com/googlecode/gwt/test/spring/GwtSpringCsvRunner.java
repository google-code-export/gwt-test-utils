package com.googlecode.gwt.test.spring;

import com.googlecode.gwt.test.internal.runner.AbstractGwtRunner;
import com.googlecode.gwt.test.internal.runner.AbstractGwtRunnerFactory;

public class GwtSpringCsvRunner extends AbstractGwtRunner {

  public GwtSpringCsvRunner(Class<?> clazz) throws Throwable {
    super(clazz);
  }

  @Override
  protected AbstractGwtRunnerFactory getRunnerFactory() {
    return new GwtSpringCsvRunnerFactory();
  }

}
