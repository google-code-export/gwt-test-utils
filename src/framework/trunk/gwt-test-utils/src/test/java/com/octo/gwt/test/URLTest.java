package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.http.client.URL;
import com.octo.gwt.test.AbstractGWTTest;

public class URLTest extends AbstractGWTTest {

	@Test
	public void checkEncodeComponent() {
		String stringToEncode = "test";

		Assert.assertEquals(stringToEncode, URL.encodeComponent(stringToEncode));
	}

}
