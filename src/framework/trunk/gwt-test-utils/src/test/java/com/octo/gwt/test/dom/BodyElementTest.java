package com.octo.gwt.test.dom;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.octo.gwt.test.AbstractGWTTest;

public class BodyElementTest extends AbstractGWTTest {

	private BodyElement b;

	@Before
	public void initDocument() {
		b = (BodyElement) Document.get().createElement("body");
	}

	@Test
	public void checkAs() {
		BodyElement asElement = BodyElement.as(b);
		Assert.assertEquals(b, asElement);
	}

}
