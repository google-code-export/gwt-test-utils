package com.octo.gwt.test17.integ.tools.integ;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.requests.ClassRequest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class Rep1bisTest {

	@Test
	public void runTest1() {
		MyGwtShell.appender = "";
		JUnitCore core = new JUnitCore();
		Result r = core.run(new ClassRequest(Rep1Test.class));
		Assert.assertEquals(2, r.getRunCount());
		Assert.assertEquals(0, r.getFailureCount());
		Assert.assertEquals("macro_End of 1st test", MyGwtShell.appender);
	}
}
