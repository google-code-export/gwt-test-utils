package com.octo.gwt.test;

import com.octo.gwt.test.internal.runner.AbstractGwtRunnerFactory;

class GwtRunnerFactory extends AbstractGwtRunnerFactory {

  @Override
  protected String getRunnerClassName(boolean hasJUnit45OrHigher) {
    if (hasJUnit45OrHigher) {
      return "org.junit.runners.BlockJUnit4ClassRunner";
    } else {
      return "org.junit.internal.runners.JUnit4ClassRunner";
    }
  }

}
