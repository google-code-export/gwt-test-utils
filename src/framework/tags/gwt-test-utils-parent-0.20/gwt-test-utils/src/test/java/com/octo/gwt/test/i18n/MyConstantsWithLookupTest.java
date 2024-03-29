package com.octo.gwt.test.i18n;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.PatchGwtConfig;

public class MyConstantsWithLookupTest extends AbstractGwtTest {

	private MyConstantsWithLookup constants;

	@Before
	public void setUpConstants() {
		constants = GWT.create(MyConstantsWithLookup.class);
	}

	@Test
	public void checkMyConstantsWithSpecialChar() {
		PatchGwtConfig.setLocale(Locale.FRENCH);

		// Test
		String hello = constants.hello();
		String goodbye = constants.goodbye();
		String[] stringArray = constants.stringArray();
		Map<String, Object> map = constants.map();
		int functionInt = constants.functionInt();
		double functionDouble = constants.functionDouble();
		float functionFloat = constants.functionFloat();
		boolean functionBoolean = constants.functionBoolean();

		// MyConstantsWithLookup specific methods
		String getString = constants.getString("hello");
		String[] getStringArray = constants.getStringArray("stringArray");
		Map<String, String> getMap = constants.getMap("map");
		int getInt = constants.getInt("functionInt");
		double getDouble = constants.getDouble("functionDouble");
		float getFloat = constants.getFloat("functionFloat");
		boolean getBoolean = constants.getBoolean("functionBoolean");

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

		// MyConstantsWithLookup specific methods assertions
		Assert.assertEquals(hello, getString);
		Assert.assertEquals(stringArray.length, getStringArray.length);
		Assert.assertEquals(stringArray[0], getStringArray[0]);
		Assert.assertEquals(stringArray[1], getStringArray[1]);
		Assert.assertEquals(stringArray[2], getStringArray[2]);

		Assert.assertEquals(map.size(), getMap.size());
		Assert.assertEquals(map.get("hello"), getMap.get("hello"));
		Assert.assertEquals(map.get("goodbye"), getMap.get("goodbye"));
		Assert.assertEquals(map.get("noCorrespondance"), getMap.get("noCorrespondance"));

		Assert.assertEquals(functionInt, getInt);
		Assert.assertEquals(functionDouble, getDouble, 0);
		Assert.assertEquals(functionFloat, getFloat, 0);
		Assert.assertEquals(functionBoolean, getBoolean);
	}

	@Test
	public void checkMyConstantsDefaultValue() {
		// Setup
		String expectedErrorMessage = "No matching property \"goodbye\" for Constants class [com.octo.gwt.test.i18n.MyConstantsWithLookup]. Please check the corresponding properties file or use @DefaultStringValue";

		// Test 1
		String hello = constants.hello();
		String[] stringArray = constants.stringArray();
		Map<String, Object> map = constants.map();
		int functionInt = constants.functionInt();
		double functionDouble = constants.functionDouble();
		float functionFloat = constants.functionFloat();
		boolean functionBoolean = constants.functionBoolean();

		// MyConstantsWithLookup specific methods
		String getString = constants.getString("hello");
		String[] getStringArray = constants.getStringArray("stringArray");
		Map<String, String> getMap = constants.getMap("map");
		int getInt = constants.getInt("functionInt");
		double getDouble = constants.getDouble("functionDouble");
		float getFloat = constants.getFloat("functionFloat");
		boolean getBoolean = constants.getBoolean("functionBoolean");

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

		// MyConstantsWithLookup specific methods assertions
		Assert.assertEquals(hello, getString);
		Assert.assertEquals(stringArray.length, getStringArray.length);
		Assert.assertEquals(stringArray[0], getStringArray[0]);
		Assert.assertEquals(stringArray[1], getStringArray[1]);

		Assert.assertEquals(map.size(), getMap.size());
		Assert.assertEquals(map.get("hello"), getMap.get("hello"));
		Assert.assertEquals(map.get("goodbye"), getMap.get("goodbye"));
		Assert.assertEquals(map.get("noCorrespondance"), getMap.get("noCorrespondance"));

		Assert.assertEquals(functionInt, getInt);
		Assert.assertEquals(functionDouble, getDouble, 0);
		Assert.assertEquals(functionFloat, getFloat, 0);
		Assert.assertEquals(functionBoolean, getBoolean);

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
