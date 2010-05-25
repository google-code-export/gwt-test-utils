package com.octo.gwt.test;

import static junit.framework.Assert.assertEquals;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.octo.gwt.test.AbstractGWTTest;
import com.octo.gwt.test.PatchGWT;

public class GWTTest extends AbstractGWTTest {

	private static String sToday;
	
	@Override
	public String getModuleConfigurationFile() {
		return "test-config.gwt.xml";
	}

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
		Assert.assertEquals("http://localhost:8888/", GWT.getHostPageBaseURL());
	}
	
	@Test
	public void checkGetModuleName() {
		Assert.assertEquals("gwt_test_utils_module", GWT.getModuleName());
	}

	@Test
	public void checkGetModuleBaseURL() {
		Assert.assertEquals("http://localhost:8888/gwt_test_utils_module/", GWT.getModuleBaseURL());
	}

}
