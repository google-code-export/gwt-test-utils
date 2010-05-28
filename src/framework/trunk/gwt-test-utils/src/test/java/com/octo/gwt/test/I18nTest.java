package com.octo.gwt.test;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.GWT;

public class I18nTest extends AbstractGwtTest {

	@Test
	public void checkI18n() {
		PatchGwtConfig.setLocale(Locale.FRENCH);

		I18nConstants constants = GWT.create(I18nConstants.class);

		Assert.assertEquals("Bonjour", constants.hello());
		Assert.assertEquals("Au revoir et un caractère qui pue", constants.goodbye());
	}

}
