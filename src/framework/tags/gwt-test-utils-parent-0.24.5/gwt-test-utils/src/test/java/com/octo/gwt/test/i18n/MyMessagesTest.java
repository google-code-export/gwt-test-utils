package com.octo.gwt.test.i18n;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtConfig;
import com.octo.gwt.test.GwtTestTest;

public class MyMessagesTest extends GwtTestTest {

	private MyMessages messages;

	@Test
	public void checkAMessage() {
		// Setup
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
		GwtConfig.setLocale(Locale.FRANCE);

		// Test 2
		result = messages.a_message("Gael", 23, true);

		// Assert2
		Assert.assertEquals("Bonjour Gael, vous avez saisi le nombre 23 et le booléen true", result);
	}

	@Test
	public void checkMeaningAnnotation() {
		// Setup 1
		GwtConfig.setLocale(Locale.FRANCE);

		// Test 1
		String orangeColor = messages.orangeColor();
		String orangeFruit = messages.orangeFruit();

		// Assert 1
		Assert.assertEquals("Orange", orangeColor);
		Assert.assertEquals("Orange", orangeFruit);

		// Setup 2
		GwtConfig.setLocale(Locale.ENGLISH);

		// Test 2
		orangeColor = messages.orangeColor();
		orangeFruit = messages.orangeFruit();

		// Assert 2
		Assert.assertEquals("orange", orangeColor);
		Assert.assertEquals("orange", orangeFruit);

	}

	@Test
	public void checkTotalAmount() {
		// Setup 1
		GwtConfig.setLocale(Locale.US);

		// Test
		String totalAmount = messages.totalAmount(6);

		// Assert 1
		Assert.assertEquals("Your cart total is $6.00", totalAmount);

		// Setup 2
		GwtConfig.setLocale(Locale.FRANCE);

		// Test 2
		totalAmount = messages.totalAmount(6);

		// Assert 2
		Assert.assertEquals("Le total de votre panier est de 6,00 €", totalAmount);
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
		GwtConfig.setLocale(Locale.FRANCE);

		// Test
		String result0 = messages.widgetCount(0);
		String result1 = messages.widgetCount(1);
		String result2 = messages.widgetCount(2);

		// Assert
		Assert.assertEquals("Vous avez 0 widget", result0);
		Assert.assertEquals("Vous avez 1 widget", result1);
		Assert.assertEquals("Vous avez 2 widgets", result2);
	}

	// @Test
	// public void checkAlternateMessageWithSelect_default_en() {
	// // Test
	// String resultFEMALE = messages.alternateMessageWithSelect("Jenny",
	// Gender.FEMALE);
	// String resultMALE = messages.alternateMessageWithSelect("Brian",
	// Gender.MALE);
	// String resultUNKNOWN = messages.alternateMessageWithSelect("Gloups",
	// Gender.UNKNOWN);
	//
	// // Assert
	// Assert.assertEquals("Jenny likes her widgets.", resultFEMALE);
	// Assert.assertEquals("Brian likes his widgets.", resultMALE);
	// Assert.assertEquals("Gloups likes their widgets.", resultUNKNOWN);
	// }
	//
	// @Test
	// public void checkAlternateMessageWithSelect_fr() {
	// // Setup
	// GwtConfig.setLocale(Locale.FRANCE);
	//
	// // Test
	// String resultFEMALE = messages.alternateMessageWithSelect("Jenny",
	// Gender.FEMALE);
	// String resultMALE = messages.alternateMessageWithSelect("Brian",
	// Gender.MALE);
	// String resultUNKNOWN = messages.alternateMessageWithSelect("Gloups",
	// Gender.UNKNOWN);
	//
	// // Assert
	// Assert.assertEquals("Jenny aime sa poupée", resultFEMALE);
	// Assert.assertEquals("Brian aime son dinosaure", resultMALE);
	// Assert.assertEquals("Gloups aime son nonosse", resultUNKNOWN);
	// }
	//
	// @Test
	// public void checkAlternateMessageWithSelectAndPluralCount_default_en() {
	// // Test
	// String resultFEMALE_ONE =
	// messages.alternateMessageWithSelectAndPluralCount("Jenny", Gender.FEMALE,
	// 1);
	// String resultFEMALE_MANY =
	// messages.alternateMessageWithSelectAndPluralCount("Jenny", Gender.FEMALE,
	// 4);
	// String resultMALE_ONE =
	// messages.alternateMessageWithSelectAndPluralCount("Brian", Gender.MALE, 1);
	// String resultMALE_MANY =
	// messages.alternateMessageWithSelectAndPluralCount("Brian", Gender.MALE, 2);
	// String resultUNKNOWN =
	// messages.alternateMessageWithSelectAndPluralCount("Gloups", Gender.UNKNOWN,
	// 0);
	//
	// // Assert
	// Assert.assertEquals("Jenny gave away her widget", resultFEMALE_ONE);
	// Assert.assertEquals("Jenny gave away her 4 widgets", resultFEMALE_MANY);
	// Assert.assertEquals("Brian gave away his widget", resultMALE_ONE);
	// Assert.assertEquals("Brian gave away his 2 widgets", resultMALE_MANY);
	// Assert.assertEquals("Gloups gave away their 0 widgets", resultUNKNOWN);
	// }
	//
	// @Test
	// public void checkAlternateMessageWithSelectAndPluralCount_fr() {
	// // Setup
	// GwtConfig.setLocale(Locale.FRANCE);
	//
	// // Test
	// String resultFEMALE_ONE =
	// messages.alternateMessageWithSelectAndPluralCount("Jenny", Gender.FEMALE,
	// 1);
	// String resultFEMALE_MANY =
	// messages.alternateMessageWithSelectAndPluralCount("Jenny", Gender.FEMALE,
	// 4);
	// String resultMALE_ONE =
	// messages.alternateMessageWithSelectAndPluralCount("Brian", Gender.MALE, 1);
	// String resultMALE_MANY =
	// messages.alternateMessageWithSelectAndPluralCount("Brian", Gender.MALE, 2);
	// String resultUNKNOWN =
	// messages.alternateMessageWithSelectAndPluralCount("Gloups", Gender.UNKNOWN,
	// 0);
	//
	// // Assert
	// Assert.assertEquals("Jenny aime sa poupée", resultFEMALE_ONE);
	// Assert.assertEquals("Jenny aime ses 4 poupées", resultFEMALE_MANY);
	// Assert.assertEquals("Brian aime son dinosaure", resultMALE_ONE);
	// Assert.assertEquals("Brian aime ses 2 dinosaures", resultMALE_MANY);
	// Assert.assertEquals("Gloups gave away their 0 widgets", resultUNKNOWN);
	// }

	@Before
	public void setUpMyMessages() {
		messages = GWT.create(MyMessages.class);
	}
}
