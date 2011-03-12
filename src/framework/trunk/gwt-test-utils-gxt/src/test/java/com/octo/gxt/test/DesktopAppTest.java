package com.octo.gxt.test;

import org.junit.Test;

import com.extjs.gxt.samples.desktop.client.DesktopApp;
import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.AbstractGwtTest;

public class DesktopAppTest extends AbstractGwtTest {

	@Override
	protected String getModuleName() {
		return "com.extjs.gxt.samples.desktop.DesktopApp";
	}

	@Test
	public void checkOnModuleLoad() {
		// Setup
		DesktopApp app = GWT.create(DesktopApp.class);

		// Test
		app.onModuleLoad();

		// Assert
	}
}
