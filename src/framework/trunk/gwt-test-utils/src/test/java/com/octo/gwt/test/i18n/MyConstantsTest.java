package com.octo.gwt.test.i18n;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.GwtConfig;

public class MyConstantsTest extends GwtTest {

	private MyConstants constants;

	@Before
	public void setUpConstants() {
		constants = GWT.create(MyConstants.class);
	}

	@Test
	public void checkMyConstantsWithSpecialChar() {
		GwtConfig.get().setLocale(Locale.FRENCH);

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

		Assert.assertEquals(3, map.size());
		Assert.assertEquals("Bonjour", map.get("hello"));
		Assert.assertEquals("Au revoir et un caractère qui pue", map.get("goodbye"));
		Assert.assertEquals("no corresponding property in any file, value from @DefaultStringValue", map.get("noCorrespondance"));

		Assert.assertEquals(4, functionInt);
		Assert.assertEquals(4.4, functionDouble, 0);
		Assert.assertEquals((float) 5.55, functionFloat, 0);
		Assert.assertTrue(functionBoolean);
	}

	@Test
	public void checkMyConstantsChange() {
		// Setup
		GwtConfig.get().setLocale(Locale.ENGLISH);

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

		Assert.assertEquals(3, map.size());
		Assert.assertEquals("Hello english !", map.get("hello"));
		Assert.assertEquals("Goodbye english !", map.get("goodbye"));
		// functionFloat key is not present in MyConstants_en.properties : loaded from @DefaultStringValue
		Assert.assertEquals((float) 6.66, map.get("functionFloat"));

		// Test 2
		GwtConfig.get().setLocale(Locale.US);
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

		// "map" is not present in MyConstants_en_US.properties : loaded from @DefaultStringMapValue
		Assert.assertEquals(1, map.size());
		Assert.assertEquals("hello from @DefaultStringValue", map.get("hello"));
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
		Assert.assertEquals("hello from @DefaultStringValue", map.get("hello"));

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

}
