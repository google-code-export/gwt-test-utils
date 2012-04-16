package com.googlecode.gxt.test;

/**
 * Class in charge of reseting all necessary GXT internal objects after the
 * execution of a unit test. <strong>For internal use only.</strong>
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public class GxtReset {

  private static final GxtReset INSTANCE = new GxtReset();

  /**
   * Return the GxtReset instance
   * 
   * @return
   */
  public static GxtReset get() {
    return INSTANCE;
  }

  private GxtReset() {

  }

  /**
   * Reset all necessary GXT internal objects.
   */
  public void reset() {
    // nothing to do yet, this class is not used in code but allows
    // maven-javadoc-plugin to generate gwt-test-utils-gxt-javadoc.jar
  }

}
