package com.googlecode.gwt.test.gxt2;

/**
 * Class in charge of reseting all necessary GXT 2.x internal objects after the
 * execution of a unit test. <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class Gxt2Reset {

  private static final Gxt2Reset INSTANCE = new Gxt2Reset();

  /**
   * Return the GxtReset instance
   * 
   * @return
   */
  public static Gxt2Reset get() {
    return INSTANCE;
  }

  private Gxt2Reset() {

  }

  /**
   * Reset all necessary GXT internal objects.
   */
  public void reset() {
    // nothing to do yet, this class is not used in code but allows
    // maven-javadoc-plugin to generate gwt-test-utils-gxt-javadoc.jar
  }

}
