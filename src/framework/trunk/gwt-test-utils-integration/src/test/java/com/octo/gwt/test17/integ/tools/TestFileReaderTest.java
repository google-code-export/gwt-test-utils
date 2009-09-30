package com.octo.gwt.test17.integ.tools;

import org.junit.Assert;
import org.junit.Test;

public class TestFileReaderTest {

	@Test
	public void checkRep1() throws Exception {
		TestFileReader reader = new TestFileReader(Rep1.class, new Launcher());
		Assert.assertEquals(2, reader.getTestList().size());
		Assert.assertEquals(2, reader.getTestMethods().size());
		Assert.assertEquals(1, reader.getMacroList().size());
	}
	
}
