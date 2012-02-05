package com.octo.gwt.test;

import static junit.framework.Assert.assertEquals;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.utils.events.Browser;

public class GwtMainTest extends GwtTestTest {

	private String sToday;
	private boolean success;

	@Test
	public void checkGetHostPageBase() {
		Assert.assertEquals("http://127.0.0.1:8888/", GWT.getHostPageBaseURL());
	}

	@Test
	public void checkGetModuleBaseURL() {
		Assert.assertEquals("http://127.0.0.1:8888/gwt_test_utils_module/",
				GWT.getModuleBaseURL());
	}

	@Test
	public void checkGetModuleName() {
		Assert.assertEquals("gwt_test_utils_module", GWT.getModuleName());
	}

	@Test
	public void checkGetVersion() {
		Assert.assertEquals("GWT 2 by gwt-test-utils", GWT.getVersion());
	}

	@Test
	public void checkIsClient() {
		Assert.assertTrue(GWT.isClient());
	}

	@Test
	public void checkIsScript() {
		Assert.assertFalse(GWT.isScript());
	}

	@Test
	public void checkRunAsync() {
		// Setup
		Button b = new Button();
		b.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				GWT.runAsync(new RunAsyncCallback() {

					public void onFailure(Throwable reason) {
						Assert.fail("GWT.runAsync() has called \"onFailure\" callback");

					}

					public void onSuccess() {
						success = true;
					}
				});

			}
		});

		// Test
		Browser.click(b);

		// Assert
		Assert.assertTrue(success);

	}

	@Test
	public void checkThatGwtInitialiseOccursBeforeTheJUnitInitialisationOfTheClass() {
		assertEquals("mer. 25 nov.", sToday);
	}

	@Before
	public void setupGWTTest() {
		GwtConfig.get().setLocale(new Locale("FR"));
		sToday = DateTimeFormat.getFormat("EEE dd MMM").format(
				new Date(1259103600000l));
		success = false;
	}
}
