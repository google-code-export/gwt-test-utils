package com.octo.gwt.test.patchers;

import javassist.ClassPool;
import javassist.CtClass;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.octo.gwt.test.internal.PatchGwtClassPool;
import com.octo.gwt.test.internal.utils.GwtPatcherUtils;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.MyClassToPatch.MyInnerClass;

public class AutomaticPatcherTest {

	private AutomaticPatcher patcher;

	@Before
	public void setUpAutomaticPatcher() {
		patcher = new MyClassToPatchPatcher();
	}

	@Test
	public void checkPatchWithInnerClassAsParam() throws Exception {
		// Setup
		ClassPool cp = PatchGwtClassPool.get();
		CtClass ctClass = cp.getAndRename(MyClassToPatch.class.getName(), "generated.NewGeneratedClass");
		ctClass.setSuperclass(cp.get(MyClassToPatch.class.getName()));
		MyInnerClass innerObject = new MyInnerClass("innerOjbectForUnitTest");

		// Test
		GwtPatcherUtils.patch(ctClass, patcher);

		//Assert
		Class<?> clazz = ctClass.toClass();
		MyClassToPatch instance = (MyClassToPatch) clazz.newInstance();

		Assert.assertEquals("myStringMethod has been patched : innerOjbectForUnitTest", instance.myStringMethod(innerObject));
	}

}
