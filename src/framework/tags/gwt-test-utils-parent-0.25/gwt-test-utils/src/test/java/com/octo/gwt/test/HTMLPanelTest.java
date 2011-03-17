package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.HTMLPanel;

public class HTMLPanelTest extends GwtTest {

	@Test
	public void checkInstanciation() {
		HTMLPanel panel = new HTMLPanel("<h1>Test</h1>");

		Assert.assertEquals("<h1>Test</h1>", panel.getElement().getInnerHTML());
	}
}
