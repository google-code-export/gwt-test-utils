package com.googlecode.gwt.test.csv;

import com.googlecode.gwt.test.internal.runner.AbstractGwtRunnerFactory;

public class GwtCsvRunnerFactory extends AbstractGwtRunnerFactory {

  @Override
  protected String getRunnerClassName(boolean hasJUnit45OrHigher) {
    return hasJUnit45OrHigher
        ? "com.googlecode.gwt.test.csv.internal.BlockJUnit4CsvRunner"
        : "com.googlecode.gwt.test.csv.internal.JUnit4CsvClassRunner";
  }

}
