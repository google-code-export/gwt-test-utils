package com.googlecode.gwt.test;

import com.googlecode.gwt.test.internal.junit.AbstractGwtRunnerFactory;

class GwtRunnerFactory extends AbstractGwtRunnerFactory {

   @Override
   protected String getRunnerClassName(boolean hasJUnit45OrHigher) {
      if (hasJUnit45OrHigher) {
         return "com.googlecode.gwt.test.internal.junit.GwtBlockJUnit4ClassRunner";
      } else {
         return "com.googlecode.gwt.test.internal.junit.GwtJUnit4ClassRunner";
      }
   }

}
