package com.googlecode.gwt.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the GWT module to test in a {@link GwtModuleRunner}.
 * 
 * @author Gael Lazzari
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface GwtModule {

  /**
   * Specifies the full qualified name of the GWT module under test
   * 
   * @return the full qualified name of the GWT module under test
   */
  String value();

}
