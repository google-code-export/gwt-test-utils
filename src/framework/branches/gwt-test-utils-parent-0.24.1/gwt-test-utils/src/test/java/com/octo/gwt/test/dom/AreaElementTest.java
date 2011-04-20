package com.octo.gwt.test.dom;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.AreaElement;
import com.google.gwt.dom.client.Document;
import com.octo.gwt.test.GwtTestTest;

public class AreaElementTest extends GwtTestTest {

	private AreaElement a;

	@Before
	public void initDocument() {
		a = Document.get().createAreaElement();
	}

	@Test
	public void checkAs() {
		AreaElement asElement = AreaElement.as(a);
		Assert.assertEquals(a, asElement);
	}

	@Test
	public void checkAccessKey() {
		Assert.assertEquals("", a.getAccessKey());
		// Set up
		a.setAccessKey("k");

		// Assert
		Assert.assertEquals("k", a.getAccessKey());
	}

	@Test
	public void checkAlt() {
		Assert.assertEquals("", a.getAlt());
		// Set up
		a.setAlt("Alt");

		// Assert
		Assert.assertEquals("Alt", a.getAlt());
	}

	@Test
	public void checkCoords() {
		Assert.assertEquals("", a.getCoords());
		// Set up
		a.setCoords("Coords");

		// Assert
		Assert.assertEquals("Coords", a.getCoords());
	}

	@Test
	public void checkHref() {
		Assert.assertEquals("", a.getHref());
		// Set up
		a.setHref("Href");

		// Assert
		Assert.assertEquals("Href", a.getHref());
	}

	@Test
	public void checkShape() {
		Assert.assertEquals("", a.getShape());
		// Set up
		a.setShape("Shape");

		// Assert
		Assert.assertEquals("Shape", a.getShape());
	}

	@Test
	public void checkTabIndex() {
		Assert.assertEquals(0, a.getTabIndex());
		// Set up
		a.setTabIndex(4);

		// Assert
		Assert.assertEquals(4, a.getTabIndex());
	}

	@Test
	public void checkTarget() {
		Assert.assertEquals("", a.getTarget());
		// Set up
		a.setTarget("Target");

		// Assert
		Assert.assertEquals("Target", a.getTarget());
	}

}
