package com.octo.gwt.test.csv;

import com.octo.gwt.test.internal.runner.AbstractGwtRunnerFactory;

public class GwtCsvRunnerFactory extends AbstractGwtRunnerFactory {

  @Override
  protected String getRunnerClassName(boolean hasJUnit45OrHigher) {
    return hasJUnit45OrHigher
        ? "com.octo.gwt.test.csv.internal.BlockJUnit4CsvRunner"
        : "com.octo.gwt.test.csv.internal.JUnit4CsvClassRunner";
  }

}
