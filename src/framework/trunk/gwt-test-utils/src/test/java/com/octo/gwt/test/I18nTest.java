package com.octo.gwt.test;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.AbstractGWTTest;
import com.octo.gwt.test.PatchGWT;

public class I18nTest extends AbstractGWTTest {

	@Test
	public void checkI18n() {
		PatchGWT.setLocale(Locale.FRENCH);

		I18nConstants constants = GWT.create(I18nConstants.class);

		Assert.assertEquals("Bonjour", constants.hello());
		Assert.assertEquals("Au revoir et un caractère qui pue", constants.goodbye());
	}

}
