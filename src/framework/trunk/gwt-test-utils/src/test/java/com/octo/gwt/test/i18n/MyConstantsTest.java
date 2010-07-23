package com.octo.gwt.test.i18n;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.PatchGwtConfig;

public class MyConstantsTest extends AbstractGwtTest {

	@Test
	public void checkMyConstants() {
		PatchGwtConfig.setLocale(Locale.FRENCH);

		MyConstants constants = GWT.create(MyConstants.class);

		Assert.assertEquals("Bonjour", constants.hello());
		Assert.assertEquals("Au revoir et un caractère qui pue", constants.goodbye());
	}

}
