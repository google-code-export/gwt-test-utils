package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class GWTTest extends AbstractGWTTest {
	
	@Test
	public void checkGetHostPageBase() {
		Assert.assertEquals("getHostPageBaseURL/getModuleName", GWT.getHostPageBaseURL());
	}
	
	@Test
	public void checkGetModuleName() {
		Assert.assertEquals("getModuleName", GWT.getModuleName());
	}
	

}
