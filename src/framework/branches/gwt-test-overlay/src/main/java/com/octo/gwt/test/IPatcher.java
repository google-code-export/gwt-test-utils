package com.octo.gwt.test;

import javassist.CtClass;
import javassist.CtMethod;

public interface IPatcher {

	public void initClass(CtClass c) throws Exception;

	public String getNewBody(CtMethod m) throws Exception;
	
	public void finalizeClass(CtClass c) throws Exception;

}
