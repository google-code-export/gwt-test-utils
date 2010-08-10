package com.octo.gwt.test.internal.patcher.tools;

import com.octo.gwt.test.internal.patcher.tools.MyClassToPatch.MyInnerClass;

@PatchClass(MyClassToPatch.class)
public class MyClassToPatchPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String myStringMethod(MyClassToPatch myClassToPatch, MyInnerClass innerObject) {
		return "myStringMethod has been patched : " + innerObject.getInnerString();
	}

}
