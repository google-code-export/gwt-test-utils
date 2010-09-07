package com.octo.gwt.test.internal.utils;

import com.octo.gwt.test.internal.utils.MyClassToPatch.MyInnerClass;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(MyClassToPatch.class)
public class MyClassToPatchPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String myStringMethod(MyClassToPatch myClassToPatch, MyInnerClass innerObject) {
		return "myStringMethod has been patched : " + innerObject.getInnerString();
	}

}
