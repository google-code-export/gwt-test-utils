package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test17.test.AbstractGWTTest;

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
