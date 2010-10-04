package com.octo.gwt.test;

import javassist.CtClass;
import javassist.CtMethod;

/**
 * Interface for a class patcher.
 * 
 * A patcher provides custom bodies (implementations) for the methods of a
 * class. Patchers are used to replace methods implementations which are not
 * suitable for a JVM (typically, those containing Javascript code) by valid
 * Java ones.
 * 
 * Although the framework provides generic patchers (which should permit to
 * produce iso-functional, Java compliant code in the majority of cases), it is
 * possible for you to create your own patcher (specifically, if you're writing
 * your own GWT component class) if the default patching mechanisms doesn't suit
 * your needs.
 * 
 * The custom patchers used to test an application are configured in the
 * META-INF\gwt-test-utils.properties file.
 * 
 */
public interface IPatcher {

	/**
	 * Initializes patching for the specified class.
	 */
	public void initClass(CtClass c) throws Exception;

	/**
	 * Returns the new body for the specified method.
	 */
	public String getNewBody(CtMethod m) throws Exception;

	/**
	 * Finalizes patching for the specified class.
	 */
	public void finalizeClass(CtClass c) throws Exception;

}
