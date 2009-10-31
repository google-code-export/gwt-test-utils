package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class TestI18n extends AbstractGWTTest {
	
	@Test
	public void checkI18n() {
		I18nConstants constants = GWT.create(I18nConstants.class);
		
		Assert.assertEquals("Bonjour", constants.hello());
		Assert.assertEquals("Au revoir", constants.goodbye());
	}

}
