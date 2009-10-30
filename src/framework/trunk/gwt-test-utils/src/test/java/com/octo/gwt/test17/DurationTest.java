package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.Duration;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class DurationTest extends AbstractGWTTest {
	
	@Test
	public void checkCurrentTimeMillis() {
		
		Assert.assertTrue("Duration should be greater than 0", Duration.currentTimeMillis() > 0);
	}

}
