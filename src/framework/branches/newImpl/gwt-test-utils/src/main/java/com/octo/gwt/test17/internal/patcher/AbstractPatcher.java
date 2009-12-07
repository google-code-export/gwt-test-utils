package com.octo.gwt.test17.internal.patcher;

import javassist.CtClass;
import javassist.CtMethod;

import com.octo.gwt.test17.PatchUtils;

public class AbstractPatcher implements Patcher {

	public void initClass(CtClass c) throws Exception {
		// nothing to do by default

	}

	public boolean patchMethod(CtMethod m) throws Exception {
		// nothing to patch by default
		return false;
	}

	protected void replaceImplementation(CtMethod m, String staticMethodName, String args) throws Exception {
		PatchUtils.replaceImplementation(m, this.getClass(), staticMethodName, args);
	}

	protected void replaceImplementation(CtMethod m, String src) throws Exception {
		PatchUtils.replaceImplementation(m, src);
	}

	protected boolean argsMatch(CtMethod m, Class<?>[] argsClasses) throws Exception {
		return PatchUtils.matches(m.getParameterTypes(), argsClasses);
	}

}
