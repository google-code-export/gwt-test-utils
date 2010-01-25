package com.octo.gwt.test17.internal.patcher;

import javassist.CtClass;
import javassist.CtMethod;

public interface Patcher {

	public void initClass(CtClass c) throws Exception;

	public String getNewBody(CtMethod m) throws Exception;
	
}
