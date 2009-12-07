package com.octo.gwt.test17;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.AreaElement;
import com.google.gwt.dom.client.Document;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class AreaElementTest extends AbstractGWTTest {

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
		Assert.assertNull("AccessKey should be null", a.getAccessKey());
		// Set up
		a.setAccessKey("k");

		// Assert
		Assert.assertEquals("k", a.getAccessKey());
	}

	@Test
	public void checkAlt() {
		Assert.assertNull("Alt should be null", a.getAlt());
		// Set up
		a.setAlt("Alt");

		// Assert
		Assert.assertEquals("Alt", a.getAlt());
	}

	@Test
	public void checkCoords() {
		Assert.assertNull("Coords should be null", a.getCoords());
		// Set up
		a.setCoords("Coords");

		// Assert
		Assert.assertEquals("Coords", a.getCoords());
	}

	@Test
	public void checkHref() {
		Assert.assertNull("Href should be null", a.getHref());
		// Set up
		a.setHref("Href");

		// Assert
		Assert.assertEquals("Href", a.getHref());
	}

	@Test
	public void checkShape() {
		Assert.assertNull("Shape should be null", a.getShape());
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
		Assert.assertNull("Target should be null", a.getTarget());
		// Set up
		a.setTarget("Target");

		// Assert
		Assert.assertEquals("Target", a.getTarget());
	}

}
