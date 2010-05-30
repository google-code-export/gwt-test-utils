package com.octo.gwt.test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public interface IPatcher {

	public void initClass(CtClass c, ClassPool cp) throws Exception;

	public String getNewBody(CtMethod m) throws Exception;
	
	public void finalizeClass(CtClass c, ClassPool cp) throws Exception;

}
