package com.octo.gwt.test.patchers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method is used to provide a JVM-compliant
 * version of a particular method.
 * 
 * @author Bertrand Paquet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PatchMethod {

	/**
	 * <p>
	 * Specify if this patch method should override an existing one, declared in
	 * another {@link PatchClass}. Only one <code>PatchMethod</code> with
	 * override = true can exist. Otherwise, an exception will be thrown.
	 * </p>
	 * <p>
	 * Default value is <strong>false</strong>.
	 * </p>
	 * 
	 * @return True is this patch method should override an existing one, false
	 *         otherwise.
	 */
	boolean override() default false;

	/**
	 * The name of the method to patch. If not set, {@link AutomaticPatcher}
	 * will check for a method with the same name as the annotated one.
	 * 
	 * @return The name of the method to patch.
	 */
	String value() default "";

}
