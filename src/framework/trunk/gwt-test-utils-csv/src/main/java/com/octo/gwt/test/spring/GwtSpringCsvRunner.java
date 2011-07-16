package com.octo.gwt.test.spring;

import com.octo.gwt.test.internal.runner.AbstractGwtRunner;
import com.octo.gwt.test.internal.runner.AbstractGwtRunnerFactory;

public class GwtSpringCsvRunner extends AbstractGwtRunner {

  public GwtSpringCsvRunner(Class<?> clazz) throws Exception {
    super(clazz);
  }

  @Override
  protected AbstractGwtRunnerFactory getRunnerFactory() {
    return new GwtSpringCsvRunnerFactory();
  }

}
