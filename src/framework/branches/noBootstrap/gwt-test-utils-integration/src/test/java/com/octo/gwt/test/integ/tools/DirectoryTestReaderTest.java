package com.octo.gwt.test.integ.tools;

import org.junit.Assert;
import org.junit.Test;

import com.octo.gwt.test.integ.tools.DirectoryTestReader;

public class DirectoryTestReaderTest {

	@Test
	public void checkRep1() throws Exception {
		DirectoryTestReader reader = new DirectoryTestReader(Rep1.class);
		Assert.assertEquals(5, reader.getTestList().size());
		Assert.assertEquals(5, reader.getTestMethods().size());
		Assert.assertEquals(1, reader.getMacroFileList().size());
	}

}
