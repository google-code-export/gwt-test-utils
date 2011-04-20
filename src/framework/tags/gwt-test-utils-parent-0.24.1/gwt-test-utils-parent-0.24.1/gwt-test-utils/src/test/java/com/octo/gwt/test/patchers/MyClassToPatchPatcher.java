package com.octo.gwt.test.patchers;

import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.patchers.MyClassToPatch.MyInnerClass;

@PatchClass(MyClassToPatch.class)
public class MyClassToPatchPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String myStringMethod(MyClassToPatch myClassToPatch, MyInnerClass innerObject) {
		return "myStringMethod has been patched : " + innerObject.getInnerString();
	}

}
