package com.octo.gwt.test;

import javassist.ClassPool;

public class PatchGwtClassPool {

	private static ClassPool classPool = ClassPool.getDefault();
	
	public static ClassPool get() {
		return classPool;
	}
}
