package com.octo.gwt.test17;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.BaseElement;
import com.google.gwt.dom.client.Document;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class BaseElementTest extends AbstractGWTTest {

	private BaseElement b;

	@Before
	public void initDocument() {
		b = Document.get().createBaseElement();
	}

	@Test
	public void checkAs() {
		BaseElement asElement = BaseElement.as(b);
		Assert.assertEquals(b, asElement);
	}

	@Test
	public void checkHref() {
		Assert.assertNull("Href should be null", b.getHref());
		// Set up
		b.setHref("Href");

		// Assert
		Assert.assertEquals("Href", b.getHref());
	}

	@Test
	public void checkTarget() {
		Assert.assertNull("Target should be null", b.getTarget());
		// Set up
		b.setTarget("Target");

		// Assert
		Assert.assertEquals("Target", b.getTarget());
	}

}
