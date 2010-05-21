package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.AbstractGWTTest;
import com.octo.gwt.test.IGWTLogHandler;
import com.octo.gwt.test.PatchGWT;

public class GWTLogTest extends AbstractGWTTest {

	private String message;
	
	private Throwable t;
	
	@Test
	public void checkLog() {
		message = null;
		t = null;
		GWT.log("toto", new Exception("e1"));
		PatchGWT.setLogHandler(new IGWTLogHandler() {
			
			public void log(String message, Throwable t) {
				GWTLogTest.this.message = message;
				GWTLogTest.this.t = t;
			}
			
		});
		Throwable t2 = new Exception("e2");
		GWT.log("titi", t2);
		
		Assert.assertEquals("titi", message);
		Assert.assertEquals(t2, t);
	}
}
