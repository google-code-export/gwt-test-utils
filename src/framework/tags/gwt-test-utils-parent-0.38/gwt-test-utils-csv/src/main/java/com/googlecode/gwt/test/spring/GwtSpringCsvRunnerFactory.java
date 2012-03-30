package com.googlecode.gwt.test.spring;

import com.googlecode.gwt.test.internal.runner.AbstractGwtRunnerFactory;

class GwtSpringCsvRunnerFactory extends AbstractGwtRunnerFactory {

  @Override
  protected String getRunnerClassName(boolean hasJUnit45OrHigher) {
    return hasJUnit45OrHigher
        ? "com.googlecode.gwt.test.spring.internal.Spring3CsvJUnit4ClassRunner"
        : "com.googlecode.gwt.test.spring.internal.Spring2CsvJUnit4ClassRunner";
  }

}
