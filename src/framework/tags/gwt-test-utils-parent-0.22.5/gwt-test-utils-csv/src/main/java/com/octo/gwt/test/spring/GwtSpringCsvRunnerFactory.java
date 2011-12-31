package com.octo.gwt.test.spring;

import com.octo.gwt.test.internal.runner.AbstractGwtRunnerFactory;

class GwtSpringCsvRunnerFactory extends AbstractGwtRunnerFactory {

  @Override
  protected String getRunnerClassName(boolean hasJUnit45OrHigher) {
    return hasJUnit45OrHigher
        ? "com.octo.gwt.test.spring.internal.Spring3CsvJUnit4ClassRunner"
        : "com.octo.gwt.test.spring.internal.Spring2CsvJUnit4ClassRunner";
  }

}
