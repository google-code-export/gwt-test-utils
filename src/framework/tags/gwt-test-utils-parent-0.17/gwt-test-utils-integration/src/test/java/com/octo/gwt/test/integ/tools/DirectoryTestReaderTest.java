package com.octo.gwt.test.integ.tools;

import org.junit.Assert;
import org.junit.Test;

public class DirectoryTestReaderTest {

	@Test
	public void checkRep1() throws Exception {
		DirectoryTestReader reader = new DirectoryTestReader(Rep1.class);
		Assert.assertEquals(5, reader.getTestList().size());
		Assert.assertEquals(5, reader.getTestMethods().size());
		Assert.assertEquals(2, reader.getMacroFileList().size());
	}

	@Test
	public void checkRep2() throws Exception {
		DirectoryTestReader reader = new DirectoryTestReader(Rep2.class);
		Assert.assertEquals(1, reader.getTestList().size());
		Assert.assertEquals(1, reader.getTestMethods().size());
		// because we set the "pattern" attribute on @CsvMacros
		Assert.assertEquals(1, reader.getMacroFileList().size());
	}
}
