package com.octo.gwt.test.i18n;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.GwtConfig;

public class MyMessagesTest extends GwtTest {

	private MyMessages messages;

	@Before
	public void setUpMyMessages() {
		messages = GWT.create(MyMessages.class);
	}

	@Test
	public void checkAMessage() {
		//Setup
		String result = null;
		String expectedException = "Unable to find a Locale specific resource file to bind with i18n interface 'com.octo.gwt.test.i18n.MyMessages' and there is no @DefaultXXXValue annotation on 'a_message' called method";

		// Test 1
		try {
			result = messages.a_message("Gael", 23, true);
			Assert.fail("The test is expected to throw an execption since 'a_message' can't be retrieve in the default property file and no @DefaultMessage is set on the method");
		} catch (Exception e) {
			Assert.assertEquals(expectedException, e.getMessage());

		}

		// Setup 2
		GwtConfig.get().setLocale(Locale.FRANCE);

		// Test 2
		result = messages.a_message("Gael", 23, true);

		// Assert2
		Assert.assertEquals("Bonjour Gael, vous avez saisi le nombre 23 et le booléen true", result);
	}

	@Test
	public void checkMeaningAnnotation() {
		// Setup 1
		GwtConfig.get().setLocale(Locale.FRANCE);

		// Test 1
		String orangeColor = messages.orangeColor();
		String orangeFruit = messages.orangeFruit();

		// Assert 1
		Assert.assertEquals("Orange", orangeColor);
		Assert.assertEquals("Orange", orangeFruit);

		// Setup 2
		GwtConfig.get().setLocale(Locale.ENGLISH);

		// Test 2
		orangeColor = messages.orangeColor();
		orangeFruit = messages.orangeFruit();

		// Assert 2
		Assert.assertEquals("orange", orangeColor);
		Assert.assertEquals("orange", orangeFruit);

	}

	@Test
	public void checkWidgetCount_default_en() {
		// Test
		String result0 = messages.widgetCount(0);
		String result1 = messages.widgetCount(1);
		String result2 = messages.widgetCount(2);

		// Assert
		Assert.assertEquals("You have 0 widgets", result0);
		Assert.assertEquals("You have 1 widget", result1);
		Assert.assertEquals("You have 2 widgets", result2);
	}

	@Test
	public void checkWidgetCount_fr() {
		// Setup
		GwtConfig.get().setLocale(Locale.FRANCE);

		// Test
		String result0 = messages.widgetCount(0);
		String result1 = messages.widgetCount(1);
		String result2 = messages.widgetCount(2);

		// Assert
		Assert.assertEquals("Vous avez 0 widget", result0);
		Assert.assertEquals("Vous avez 1 widget", result1);
		Assert.assertEquals("Vous avez 2 widgets", result2);
	}

	@Test
	public void checkTotalAmount() {
		// Setup 1
		GwtConfig.get().setLocale(Locale.US);

		// Test
		String totalAmount = messages.totalAmount(6);

		// Assert 1
		Assert.assertEquals("Your cart total is $6.00", totalAmount);

		// Setup 2
		GwtConfig.get().setLocale(Locale.FRANCE);

		// Test 2
		totalAmount = messages.totalAmount(6);

		// Assert 2
		Assert.assertEquals("Le total de votre panier est de 6,00 €", totalAmount);
	}
}
