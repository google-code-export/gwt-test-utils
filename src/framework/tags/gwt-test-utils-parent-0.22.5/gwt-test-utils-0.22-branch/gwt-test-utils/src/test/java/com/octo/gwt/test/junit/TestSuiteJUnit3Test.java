package com.octo.gwt.test.junit;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.octo.gwt.test.dom.AnchorElementTest;
import com.octo.gwt.test.dom.AreaElementTest;

public class TestSuiteJUnit3Test {

  public static Test suite() {
    TestSuite suite = new TestSuite("JUnit3TestSuite");
    suite.addTest(new AnchorElementTest());
    suite.addTest(new AreaElementTest());
    return suite;
  }

}
