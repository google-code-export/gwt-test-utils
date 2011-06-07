package com.octo.gwt.test.patchers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method can be used to provide a JVM-compliant
 * version of a particular method.
 * 
 * @see AutomaticPatcher
 * 
 * @author Bertrand Paquet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PatchMethod {

  /**
   * The type of the patch to apply to the method.
   * 
   * @author Bertrand Paquet
   * 
   */
  public enum Type {

    /**
     * Insert new Java code after the actual body. The corresponding
     * substitution method should be static, return a String and do not take any
     * parameter (or specify its parameter through {@link PatchMethod#args()} to
     * distinguish to method with the same name).
     */
    INSERT_CODE_AFTER,

    /**
     * Insert new Java code before the actual body. The corresponding
     * substitution method should be static, return a String and do not take any
     * parameter (or specify its parameter through {@link PatchMethod#args()} to
     * distinguish to method with the same name).
     */
    INSERT_CODE_BEFORE,

    /**
     * Replace the method body with new Java Code. The corresponding
     * substitution method should be static, return a String and do not take any
     * parameter (or specify its parameter through {@link PatchMethod#args()} to
     * distinguish to method with the same name).
     */
    NEW_CODE_AS_STRING,

    /**
     * Replace the method body with a new Java Code that just call the
     * substitution method, passing it the parameter that are passed to the
     * original one, plus the object instance on which is called the method if
     * it is not static.
     */
    STATIC_CALL;
  }

  /**
   * <p>
   * The parameter of the method to patch. If not set and
   * {@link PatchMethod#type()} is set to {@link Type#STATIC_CALL},
   * {@link AutomaticPatcher} will check for a method with the same
   * {@link PatchMethod#value()} and the same parameters as the annoted one,
   * with eventually an instance of the corresponding class as the first
   * parameter if the method to patch is not static.
   * 
   * @return The parameter of the method to patch.
   */
  Class<?>[] args() default {PatchMethod.class};

  /**
   * The type of the patch. {@link Type#STATIC_CALL} by default.
   * 
   * @return The type of the patch.
   */
  Type type() default Type.STATIC_CALL;

  /**
   * The name of the method to patch. If not set, {@link AutomaticPatcher} will
   * check for a method with the same name as the annotated one.
   * 
   * @return The name of the method to patch.
   */
  String value() default "";

}
