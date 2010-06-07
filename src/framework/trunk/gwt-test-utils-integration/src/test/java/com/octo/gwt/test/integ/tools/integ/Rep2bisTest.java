package com.octo.gwt.test.integ.tools.integ;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.requests.ClassRequest;
import org.junit.internal.requests.FilterRequest;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.manipulation.Filter;

public class Rep2bisTest {

	@Test
	public void runTest1() {
		MyStringStore.appender = "";
		JUnitCore core = new JUnitCore();
		Result r = core.run(new FilterRequest(new ClassRequest(Rep2Test.class), new Filter() {

			@Override
			public boolean shouldRun(Description arg0) {
				return arg0.getDisplayName().startsWith("run_2_test1");
			}

			@Override
			public String describe() {
				return "toto";
			}
		}));
		Assert.assertEquals(1, r.getRunCount());
		Assert.assertEquals(0, r.getFailureCount());
		Assert.assertEquals("macro_totomacro_titimacro_toto_tatamacro_t0_t1_t2macro_t0_t1_t2_t3macro_t0_t1_t2_t3_t4End of 1st test",
				MyStringStore.appender);
	}
}
