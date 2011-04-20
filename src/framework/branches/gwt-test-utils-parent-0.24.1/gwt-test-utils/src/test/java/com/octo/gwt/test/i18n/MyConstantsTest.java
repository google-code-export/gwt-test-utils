package com.octo.gwt.test.i18n;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtConfig;
import com.octo.gwt.test.GwtTestTest;

public class MyConstantsTest extends GwtTestTest {

	private MyConstants constants;

	@Test
	public void checkMyConstantsChange() {
		// Setup
		GwtConfig.setLocale(Locale.ENGLISH);

		// Test 1
		String hello = constants.hello();
		String goodbye = constants.goodbye();
		String[] stringArray = constants.stringArray();
		Map<String, Object> map = constants.map();

		// Assert 1
		Assert.assertEquals("Hello english !", hello);
		Assert.assertEquals("Goodbye english !", goodbye);

		Assert.assertEquals(3, stringArray.length);
		Assert.assertEquals("one", stringArray[0]);
		Assert.assertEquals("two", stringArray[1]);
		Assert.assertEquals("three", stringArray[2]);

		Assert.assertEquals(5, map.size());
		Assert.assertEquals("Hello english !", map.get("hello"));
		Assert.assertEquals("Goodbye english !", map.get("goodbye"));
		Assert.assertEquals("glad to work with gwt-test-utils", map.get("map1"));
		Assert.assertEquals("hehe, it roxs !", map.get("map2"));
		Assert.assertNull(map.get("map3"));

		// Test 2
		GwtConfig.setLocale(Locale.US);
		hello = constants.hello();
		goodbye = constants.goodbye();
		stringArray = constants.stringArray();
		map = constants.map();

		// Assert 2
		Assert.assertEquals("Hello US !", hello);
		Assert.assertEquals("Goodbye US !", goodbye);

		Assert.assertEquals(4, stringArray.length);
		Assert.assertEquals("one", stringArray[0]);
		Assert.assertEquals("two", stringArray[1]);
		Assert.assertEquals("three", stringArray[2]);
		Assert.assertEquals("four", stringArray[3]);

		// "map" is not present in MyConstants_en_US.properties : loaded from
		// @DefaultStringMapMapValue
		Assert.assertEquals(3, map.size());
		Assert.assertEquals("default map1 value", map.get("map1"));
		Assert.assertEquals("default map2 value", map.get("map2"));
		Assert.assertEquals("default map3 value", map.get("map3"));
	}

	@Test
	public void checkMyConstantsDefaultValue() {
		// Setup
		String expectedErrorMessage = "No matching property \"goodbye\" for Constants class [com.octo.gwt.test.i18n.MyConstants]. Please check the corresponding properties file or use @DefaultStringValue";

		// Test 1
		String hello = constants.hello();
		String[] stringArray = constants.stringArray();
		Map<String, Object> map = constants.map();
		int functionInt = constants.functionInt();
		double functionDouble = constants.functionDouble();
		float functionFloat = constants.functionFloat();
		boolean functionBoolean = constants.functionBoolean();

		// Assert
		Assert.assertEquals("hello from @DefaultStringValue", hello);
		Assert.assertEquals(2, stringArray.length);
		Assert.assertEquals("default0", stringArray[0]);
		Assert.assertEquals("default1", stringArray[1]);
		Assert.assertEquals(3, map.size());
		Assert.assertEquals("default map1 value", map.get("map1"));
		Assert.assertEquals("default map2 value", map.get("map2"));
		Assert.assertEquals("default map3 value", map.get("map3"));

		Assert.assertEquals(6, functionInt);
		Assert.assertEquals(6.6, functionDouble, 0);
		Assert.assertEquals((float) 6.66, functionFloat, 0);
		Assert.assertTrue(functionBoolean);

		// Test 2 : no @DefaultStringValue
		try {
			constants.goodbye();
			Assert.fail("i18n patching mechanism should throw an exception if no locale and no @DefaultStringValue is set");
		} catch (Exception e) {
			// Assert 2
			Assert.assertEquals(expectedErrorMessage, e.getMessage());
		}
	}

	@Test
	public void checkMyConstantsWithSpecialChar() {
		GwtConfig.setLocale(Locale.FRENCH);

		// Test
		String hello = constants.hello();
		String goodbye = constants.goodbye();
		String[] stringArray = constants.stringArray();
		Map<String, Object> map = constants.map();
		int functionInt = constants.functionInt();
		double functionDouble = constants.functionDouble();
		float functionFloat = constants.functionFloat();
		boolean functionBoolean = constants.functionBoolean();

		// Assert
		Assert.assertEquals("Bonjour", hello);
		Assert.assertEquals("Au revoir et un caractère qui pue", goodbye);

		Assert.assertEquals(3, stringArray.length);
		Assert.assertEquals("un", stringArray[0]);
		Assert.assertEquals("deux", stringArray[1]);
		Assert.assertEquals("trois", stringArray[2]);

		Assert.assertEquals(5, map.size());
		Assert.assertEquals("Bonjour", map.get("hello"));
		Assert.assertEquals("Au revoir et un caractère qui pue", map.get("goodbye"));
		Assert.assertEquals("je suis content", map.get("map1"));
		Assert.assertEquals("tout pareil !", map.get("map2"));
		Assert.assertNull(map.get("map3"));

		Assert.assertEquals(4, functionInt);
		Assert.assertEquals(4.4, functionDouble, 0);
		Assert.assertEquals((float) 5.55, functionFloat, 0);
		Assert.assertTrue(functionBoolean);
	}

	@Before
	public void setUpConstants() {
		constants = GWT.create(MyConstants.class);
	}

}
