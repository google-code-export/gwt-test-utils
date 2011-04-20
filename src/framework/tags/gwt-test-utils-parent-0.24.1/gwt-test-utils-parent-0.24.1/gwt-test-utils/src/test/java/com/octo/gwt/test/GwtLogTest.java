package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.GWT;

public class GwtLogTest extends GwtTest {

	private String message;

	private Throwable t;

	@Test
	public void checkLog() {
		message = null;
		t = null;
		GWT.log("toto", new Exception("e1"));
		GwtConfig.setLogHandler(new GwtLogHandler() {

			public void log(String message, Throwable t) {
				GwtLogTest.this.message = message;
				GwtLogTest.this.t = t;
			}

		});
		Throwable t2 = new Exception("e2");
		GWT.log("titi", t2);

		Assert.assertEquals("titi", message);
		Assert.assertEquals(t2, t);
	}
}
