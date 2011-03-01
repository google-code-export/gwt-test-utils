package com.octo.gxt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.extjs.gxt.ui.client.core.El;
import com.octo.gwt.test.AbstractGwtTest;

public class ElTest extends AbstractGwtTest {

	@Test
	public void checkAddUnitsComplete() {
		// Test
		String result = El.addUnits("350em", "%");

		//Assert
		Assert.assertEquals("350em", result);
	}

	@Test
	public void checkAddUnitsWithWhitespaces() {
		// Test
		String result = El.addUnits(" 350 em ", "pt");

		//Assert
		Assert.assertEquals("350em", result);
	}

	@Test
	public void checkAddUnitsAuto() {
		// Test
		String result = El.addUnits("auto", "px");

		//Assert
		Assert.assertEquals("auto", result);
	}

	@Test
	public void checkAddUnitsNull() {
		// Test
		String result = El.addUnits(null, "px");

		//Assert
		Assert.assertEquals("", result);
	}

	@Test
	public void checkAddUnitsEmpty() {
		// Test
		String result = El.addUnits("", "px");

		//Assert
		Assert.assertEquals("", result);
	}

	@Test
	public void checkAddUnitsUndefined() {
		// Test
		String result = El.addUnits("undefined", "px");

		//Assert
		Assert.assertEquals("", result);
	}

	@Test
	public void checkAddUnitsNoUnit() {
		// Test
		String result = El.addUnits("200", "em");

		//Assert
		Assert.assertEquals("200em", result);
	}

	@Test
	public void checkAddUnitsNoUnitAndNoDefault() {
		// Test
		String result = El.addUnits("250", null);

		//Assert
		Assert.assertEquals("250px", result);
	}

	@Test
	public void checkAddUnitsNoUnitAndEmptyDefault() {
		// Test
		String result = El.addUnits("250", "");

		//Assert
		Assert.assertEquals("250px", result);
	}
}
