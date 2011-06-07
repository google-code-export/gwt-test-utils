package com.octo.gwt.test.patchers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class can be used to generate a JVM-compliant
 * version of a particular class.
 * 
 * @author Bertrand Paquet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PatchClass {

  /**
   * Name of the classes to patch, in case some of the class to patch are
   * internal.
   * 
   * @return An array containing the name of classes to patch.
   */
  String[] classes() default {};

  /**
   * Classes to patch.
   * 
   * @return An array containing the classes to patch.
   */
  Class<?>[] value() default {};

}
