package com.googlecode.gwt.test.csv;

import com.googlecode.gwt.test.internal.runner.AbstractGwtRunner;
import com.googlecode.gwt.test.internal.runner.AbstractGwtRunnerFactory;

public class GwtCsvRunner extends AbstractGwtRunner {

  public GwtCsvRunner(Class<?> clazz) throws Throwable {
    super(clazz);
  }

  @Override
  protected AbstractGwtRunnerFactory getRunnerFactory() {
    return new GwtCsvRunnerFactory();
  }

}
