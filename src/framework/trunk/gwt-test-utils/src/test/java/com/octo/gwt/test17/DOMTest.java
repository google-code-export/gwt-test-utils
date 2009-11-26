package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class DOMTest extends AbstractGWTTest {

	@Test
	public void checkCreateH1Element() {

		HeadingElement h1 = Document.get().createHElement(1);

		Assert.assertEquals("h1", h1.getTagName());
	}

	@Test
	public void checkCreateH2Element() {

		HeadingElement h2 = Document.get().createHElement(2);

		Assert.assertEquals("h2", h2.getTagName());
	}

	@Test
	public void checkCreateH3Element() {

		HeadingElement h3 = Document.get().createHElement(3);

		Assert.assertEquals("h3", h3.getTagName());
	}

	@Test
	public void checkCreateH4Element() {

		HeadingElement h4 = Document.get().createHElement(4);

		Assert.assertEquals("h4", h4.getTagName());
	}

	@Test
	public void checkCreateH5Element() {

		HeadingElement h5 = Document.get().createHElement(5);

		Assert.assertEquals("h5", h5.getTagName());
	}

	@Test
	public void checkCreateH6Element() {

		HeadingElement h6 = Document.get().createHElement(6);

		Assert.assertEquals("h6", h6.getTagName());
	}

	@Test
	public void checkCreateUListElement() {

		UListElement ul = (UListElement) Document.get().createElement("ul");

		Assert.assertEquals("ul", ul.getTagName());
	}

	@Test
	public void checkCreateLIElement() {

		LIElement ul = (LIElement) Document.get().createElement("li");

		Assert.assertEquals("li", ul.getTagName());
	}

}
