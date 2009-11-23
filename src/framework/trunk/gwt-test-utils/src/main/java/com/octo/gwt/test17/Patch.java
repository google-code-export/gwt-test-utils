package com.octo.gwt.test17;

import javassist.CtClass;

public interface Patch {

	public void apply(CtClass classToPatch) throws Exception;

}
