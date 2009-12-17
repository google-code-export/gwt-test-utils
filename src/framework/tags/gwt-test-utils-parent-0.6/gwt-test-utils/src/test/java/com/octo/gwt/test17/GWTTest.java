package com.octo.gwt.test17;

import static junit.framework.Assert.assertEquals;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class GWTTest extends AbstractGWTTest {

	private static String sToday;

	@BeforeClass
	public static void initToday() throws Exception {
		PatchGWT.setLocale(new Locale("fr"));
		sToday = DateTimeFormat.getFormat("EEE dd MMM").format(new Date(1259103600000l));
	}

	@Test
	public void checkThatGwtInitialiseOccursBeforeTheJUnitInitialisationOfTheClass() {
		assertEquals("mer. 25 nov.", sToday);
	}

	@Test
	public void checkGetHostPageBase() {
		Assert.assertEquals("getHostPageBaseURL/getModuleName", GWT.getHostPageBaseURL());
	}

	@Test
	public void checkGetModuleName() {
		Assert.assertEquals("getModuleName", GWT.getModuleName());
	}

}
