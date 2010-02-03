package com.octo.gwt.test17;

import javassist.CtClass;

public interface Patch {

	public static final String INSERT_BEFORE = "INSERT_BEFORE ";

	public void apply(CtClass classToPatch) throws Exception;

}
