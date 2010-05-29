package com.octo.gwt.test;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.i18n.client.NumberFormat;
import com.octo.gwt.test.utils.PatchGwtUtils;

public class NumberFormatTest extends AbstractGwtTest {

	@Test
	public void checkNumberFormatFr() throws Exception {
		PatchGwtConfig.setLocale(Locale.FRENCH);

		PatchGwtUtils.replaceSequenceInProperties("\\u00A0", " ");

		Assert.assertEquals("10,00 €", NumberFormat.getCurrencyFormat().format(
				10));
		Assert.assertEquals("3,142", NumberFormat.getDecimalFormat().format(
				3.1416));
	}

	@Test
	public void checkNumberFormatUs() {
		PatchGwtConfig.setLocale(Locale.US);

		Assert.assertEquals("$10.00", NumberFormat.getCurrencyFormat().format(
				10));
		Assert.assertEquals("3.142", NumberFormat.getDecimalFormat().format(
				3.1416));
	}

	@Test
	public void checkNumberFormatWithSpecificPattern() {
		// Set Up
		PatchGwtConfig.setLocale(Locale.FRENCH);
		NumberFormat numberFormat = NumberFormat.getFormat("0000000000");

		// Test
		String numberString = numberFormat.format(1234);

		// Assert
		Assert.assertEquals("0000001234", numberString);
	}

	@Test
	public void checkNumberFormatWithSpecificPatternWithDouble() {
		// Set Up
		PatchGwtConfig.setLocale(Locale.FRENCH);
		NumberFormat numberFormat = NumberFormat.getFormat("0000000000");

		// Test
		String numberString = numberFormat.format(42147482);

		// Assert
		Assert.assertEquals("0042147482", numberString);
	}

}
