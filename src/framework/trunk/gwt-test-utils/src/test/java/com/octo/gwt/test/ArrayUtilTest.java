package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.octo.gwt.test.utils.ArrayUtils;

public class ArrayUtilTest {

	@Test
	public void checkContains() {

		String[] strings = new String[] { "test1", "test2", "test3" };

		Assert.assertTrue(ArrayUtils.contains(strings, "test1"));
		Assert.assertTrue(ArrayUtils.contains(strings, "test2"));
		Assert.assertTrue(ArrayUtils.contains(strings, "test3"));
		Assert.assertFalse(ArrayUtils.contains(strings, "test4"));
	}

}
