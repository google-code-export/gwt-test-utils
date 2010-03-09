package com.octo.gwt.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import org.junit.Test;

import com.octo.gwt.test.AbstractGWTTest;

public class PatchConstructorTest extends AbstractGWTTest {

	@Test
	public void checkThatConstructorArePatched() throws Exception {
		//Fisrt check original constructors
		new SimpleClassForPatchMethod();
		assertEquals("Qwerty", new SimpleClassForPatchMethod("Qwerty").mInternal);

		PatchGWT.patch(SimpleClassForPatchMethod.class, new Patch[] {
				new PatchConstructor("throw new RuntimeException(\"Check one.\");", new Class<?>[] {}),
				new PatchConstructor("throw new RuntimeException(\"Check two.\");", new Class<?>[] { String.class }) });
		try {
			new SimpleClassForPatchMethod();
			fail("An exception should occured !");
		} catch (RuntimeException e) {
			assertEquals("Check one.", e.getMessage());
		}
		try {
			new SimpleClassForPatchMethod("Qwerty");
			fail("An exception should occured !");
		} catch (RuntimeException e) {
			assertEquals("Check two.", e.getMessage());
		}

	}

	public static class SimpleClassForPatchMethod {
		private String mInternal;

		private SimpleClassForPatchMethod() {
		}

		private SimpleClassForPatchMethod(String pParam) {
			mInternal = pParam;
		}
	}

}
