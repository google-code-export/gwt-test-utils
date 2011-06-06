package com.octo.gwt.test.patcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class can be used to generate a JVM-compliant
 * version of a particular class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PatchClass {

  Class<?>[] value() default {};

  String[] classes() default {};

}
