package com.octo.gwt.test.csv.tools;

import org.junit.Assert;
import org.junit.Test;

import com.octo.gwt.test.csv.Rep1;
import com.octo.gwt.test.csv.Rep2;
import com.octo.gwt.test.csv.tools.DirectoryTestReader;

public class DirectoryTestReaderTest {

	@Test
	public void checkRep1() throws Exception {
		DirectoryTestReader reader = new DirectoryTestReader(Rep1.class);
		Assert.assertEquals(4, reader.getTestList().size());
		Assert.assertEquals(4, reader.getTestMethods().size());
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
