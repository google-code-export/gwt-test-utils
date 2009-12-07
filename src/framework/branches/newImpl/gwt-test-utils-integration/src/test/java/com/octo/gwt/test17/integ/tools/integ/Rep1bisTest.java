package com.octo.gwt.test17.integ.tools.integ;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.requests.ClassRequest;
import org.junit.internal.requests.FilterRequest;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.manipulation.Filter;

public class Rep1bisTest {

	@Test
	public void runTest1() {
		MyGwtShell.appender = "";
		JUnitCore core = new JUnitCore();
		Result r = core.run(new FilterRequest(new ClassRequest(Rep1Test.class), new Filter() {

			@Override
			public boolean shouldRun(Description arg0) {
				return arg0.getDisplayName().startsWith("run_1_test1");
			}

			@Override
			public String describe() {
				return "toto";
			}
		}));
		Assert.assertEquals(1, r.getRunCount());
		Assert.assertEquals(0, r.getFailureCount());
		Assert.assertEquals("macro_End of 1st test", MyGwtShell.appender);
	}
}
