package com.octo.gwt.test.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.octo.gwt.test.dom.AnchorElementTest;
import com.octo.gwt.test.dom.AreaElementTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({AnchorElementTest.class, AreaElementTest.class})
public class TestSuiteJUnit4Test {

}
