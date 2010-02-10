package com.octo.gwt.test17;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.i18n.client.NumberFormat;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class NumberFormatTest extends AbstractGWTTest {

	@Test
	public void checkNumberFormatFr() throws Exception {
		PatchGWT.setLocale(Locale.FRENCH);

		PatchUtils.replaceSequenceInProperties("\\u00A0", " ");

		Assert.assertEquals("10,00 €", NumberFormat.getCurrencyFormat().format(
				10));
		Assert.assertEquals("3,142", NumberFormat.getDecimalFormat().format(
				3.1416));
	}

	@Test
	public void checkNumberFormatUs() {
		PatchGWT.setLocale(Locale.US);

		Assert.assertEquals("$10.00", NumberFormat.getCurrencyFormat().format(
				10));
		Assert.assertEquals("3.142", NumberFormat.getDecimalFormat().format(
				3.1416));
	}

	@Test
	public void checkNumberFormatWithSpecificPattern() {
		// Set Up
		PatchGWT.setLocale(Locale.FRENCH);
		NumberFormat numberFormat = NumberFormat.getFormat("000");

		// Test
		String numberString = numberFormat.format(42);

		// Assert
		Assert.assertEquals("042", numberString);
	}

}
