package com.octo.gwt.test17;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class AnchorElementTest extends AbstractGWTTest {

	private AnchorElement a;

	@Before
	public void initDocument() {
		a = Document.get().createAnchorElement();
	}

	@Test
	public void checkAs() {
		AnchorElement asElement = AnchorElement.as(a);
		Assert.assertEquals(a, asElement);
	}

	@Test
	public void checkBlur() {
		// just check blur() does not throw any exception
		a.blur();
	}

	@Test
	public void checkFocus() {
		// just check focus() does not throw any exception
		a.focus();
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
	public void checkHref() {
		Assert.assertNull("Href should be null", a.getHref());
		// Set up
		a.setHref("Href");

		// Assert
		Assert.assertEquals("Href", a.getHref());
	}

	@Test
	public void checkHreflang() {
		Assert.assertNull("Hreflang should be null", a.getHreflang());
		// Set up
		a.setHreflang("Href");

		// Assert
		Assert.assertEquals("Href", a.getHreflang());
	}

	@Test
	public void checkName() {
		Assert.assertNull("Name should be null", a.getName());
		// Set up
		a.setName("Name");

		// Assert
		Assert.assertEquals("Name", a.getName());
	}

	@Test
	public void checkRel() {
		Assert.assertNull("Rel should be null", a.getRel());
		// Set up
		a.setRel("Rel");

		// Assert
		Assert.assertEquals("Rel", a.getRel());
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

	@Test
	public void checkType() {
		Assert.assertNull("Type should be null", a.getType());
		// Set up
		a.setType("Type");

		// Assert
		Assert.assertEquals("Type", a.getType());
	}
}
