package com.octo.gwt.test17.dom;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.BRElement;
import com.google.gwt.dom.client.Document;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class BRElementTest extends AbstractGWTTest {

	private BRElement b;

	@Before
	public void initDocument() {
		b = Document.get().createBRElement();
	}

	@Test
	public void checkAs() {
		BRElement asElement = BRElement.as(b);
		Assert.assertEquals(b, asElement);
	}

}
