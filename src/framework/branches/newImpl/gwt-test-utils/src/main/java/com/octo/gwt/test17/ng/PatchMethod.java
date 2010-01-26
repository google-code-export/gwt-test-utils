package com.octo.gwt.test17.ng;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PatchMethod {

	PatchType value() default PatchType.STATIC_CALL;
	
	Class<?> [] args() default {PatchMethod.class};
	
	String methodName() default "";
	
}
