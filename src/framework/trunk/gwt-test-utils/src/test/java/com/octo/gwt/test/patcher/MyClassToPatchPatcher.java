package com.octo.gwt.test.patcher;

import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.patcher.MyClassToPatch.MyInnerClass;

@PatchClass(MyClassToPatch.class)
public class MyClassToPatchPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String myStringMethod(MyClassToPatch myClassToPatch, MyInnerClass innerObject) {
		return "myStringMethod has been patched : " + innerObject.getInnerString();
	}

}
