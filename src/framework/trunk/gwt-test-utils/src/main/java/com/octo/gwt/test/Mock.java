package com.octo.gwt.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated field should be initialized as a mock instance
 * of the field type.
 * 
 * The class declaring such a field must extend AbstractGwtEasyMockTest in order
 * to make the mock injection possible.
 * 
 * Mock objects initialized using this annotation will be replayed, verified and
 * reseted when calling the corresponding methods of AbstractGwtEasyMockTest.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mock {

}
