package com.octo.gwt.test17.integ.tools;

import org.junit.Assert;
import org.junit.Test;

public class DirectoryTestReaderTest {

	@Test
	public void checkRep1() throws Exception {
		DirectoryTestReader reader = new DirectoryTestReader(Rep1.class);
		Assert.assertEquals(3, reader.getTestList().size());
		Assert.assertEquals(3, reader.getTestMethods().size());
		Assert.assertEquals(1, reader.getMacroFileList().size());
	}

}
