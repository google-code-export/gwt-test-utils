package com.octo.gwt.test.dom;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.internal.utils.ElementUtils;

public class ElementUtilsTest extends AbstractGwtTest {

	@Test
	public void checkCast() throws Exception {
		// Set up
		AnchorElement a = Document.get().createAnchorElement();
		a.setAttribute("input", "text");
		a.setClassName("clazz");
		a.setDir("dir");

		// Test
		Element newElem = ElementUtils.castToUserElement(a);

		// Assert
		Assert.assertEquals("text", newElem.getAttribute("input"));
		Assert.assertEquals("clazz", newElem.getClassName());
		Assert.assertEquals("dir", newElem.getDir());

	}

}
