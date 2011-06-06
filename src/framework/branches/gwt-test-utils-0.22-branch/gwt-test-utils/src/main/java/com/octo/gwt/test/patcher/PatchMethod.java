package com.octo.gwt.test.patcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method can be used to provide a JVM-compliant
 * version of a particular method.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PatchMethod {

  PatchType value() default PatchType.STATIC_CALL;

  Class<?>[] args() default {PatchMethod.class};

  String methodName() default "";

}
