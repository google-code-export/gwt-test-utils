package com.octo.gwt.test17.dom;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class DocumentTest extends AbstractGWTTest {

	private Document d;

	@Before
	public void initDocument() {
		d = Document.get();
	}

	@Test
	public void checkCreateElement() {
		Assert.assertEquals("a", d.createAnchorElement().getTagName());
		Assert.assertEquals("area", d.createAreaElement().getTagName());
		Assert.assertEquals("base", d.createBaseElement().getTagName());
		Assert.assertEquals("body", d.createElement("body").getTagName());
		Assert.assertEquals("br", d.createBRElement().getTagName());
		Assert.assertEquals("div", d.createDivElement().getTagName());
		Assert.assertEquals("dl", d.createDLElement().getTagName());
		Assert.assertEquals("fieldset", d.createFieldSetElement().getTagName());
		Assert.assertEquals("form", d.createFormElement().getTagName());
		Assert.assertEquals("frame", d.createFrameElement().getTagName());
		Assert.assertEquals("frameset", d.createFrameSetElement().getTagName());
		Assert.assertEquals("head", d.createHeadElement().getTagName());
		Assert.assertEquals("h1", d.createHElement(1).getTagName());
		Assert.assertEquals("h2", d.createHElement(2).getTagName());
		Assert.assertEquals("h3", d.createHElement(3).getTagName());
		Assert.assertEquals("h4", d.createHElement(4).getTagName());
		Assert.assertEquals("h5", d.createHElement(5).getTagName());
		Assert.assertEquals("h6", d.createHElement(6).getTagName());
		Assert.assertEquals("hr", d.createHRElement().getTagName());
		Assert.assertEquals("iframe", d.createIFrameElement().getTagName());
		Assert.assertEquals("img", d.createImageElement().getTagName());
		Assert.assertEquals("input", d.createElement("input").getTagName());
		Assert.assertEquals("label", d.createLabelElement().getTagName());
		Assert.assertEquals("legend", d.createLegendElement().getTagName());
		Assert.assertEquals("li", d.createLIElement().getTagName());
		Assert.assertEquals("link", d.createLinkElement().getTagName());
		Assert.assertEquals("map", d.createMapElement().getTagName());
		Assert.assertEquals("meta", d.createMetaElement().getTagName());
		Assert.assertEquals("ins", d.createInsElement().getTagName());
		Assert.assertEquals("del", d.createDelElement().getTagName());
		Assert.assertEquals("object", d.createObjectElement().getTagName());
		Assert.assertEquals("ol", d.createOLElement().getTagName());
		Assert.assertEquals("optgroup", d.createOptGroupElement().getTagName());
		Assert.assertEquals("option", d.createOptionElement().getTagName());
		Assert.assertEquals("p", d.createPElement().getTagName());
		Assert.assertEquals("param", d.createParamElement().getTagName());
		Assert.assertEquals("pre", d.createPreElement().getTagName());
		Assert.assertEquals("q", d.createQElement().getTagName());
		Assert.assertEquals("blockquote", d.createBlockQuoteElement().getTagName());
		Assert.assertEquals("script", d.createScriptElement().getTagName());
		Assert.assertEquals("select", d.createSelectElement().getTagName());
		Assert.assertEquals("span", d.createSpanElement().getTagName());
		Assert.assertEquals("style", d.createStyleElement().getTagName());
		Assert.assertEquals("caption", d.createCaptionElement().getTagName());
		Assert.assertEquals("td", d.createTDElement().getTagName());
		Assert.assertEquals("th", d.createTHElement().getTagName());
		Assert.assertEquals("col", d.createColElement().getTagName());
		Assert.assertEquals("colgroup", d.createColGroupElement().getTagName());
		Assert.assertEquals("table", d.createTableElement().getTagName());
		Assert.assertEquals("tbody", d.createTBodyElement().getTagName());
		Assert.assertEquals("tfoot", d.createTFootElement().getTagName());
		Assert.assertEquals("thead", d.createTHeadElement().getTagName());
		Assert.assertEquals("textarea", d.createTextAreaElement().getTagName());
		Assert.assertEquals("title", d.createTitleElement().getTagName());
		Assert.assertEquals("ul", d.createULElement().getTagName());
	}

	@Test
	public void checkCreateImageInputElement() {
		// Test
		InputElement e = d.createImageInputElement();

		// Assert
		Assert.assertEquals("image", e.getType());
	}

	@Test
	public void checkCreateRadioInputElement() {
		// Test
		InputElement e = d.createRadioInputElement("test");

		// Assert
		Assert.assertEquals("RADIO", e.getType());
		Assert.assertEquals("test", e.getName());
	}

	@Test
	public void checkCreateSubmitButtonElement() {
		// Test
		ButtonElement e = d.createSubmitButtonElement();

		// Assert
		Assert.assertEquals("button", e.getTagName());
		Assert.assertEquals("submit", e.getType());
	}

	@Test
	public void checkCreatePushButtonElement() {
		// Test
		ButtonElement e = d.createPushButtonElement();

		// Assert
		Assert.assertEquals("button", e.getTagName());
		Assert.assertEquals("button", e.getType());
	}
	
	@Test
	public void checkIsCompat() {
		// Test
		boolean result = d.isCSS1Compat();

		// Assert
		Assert.assertEquals(false, result);
	}
	
	@Test
	public void checkBodyLeft() {
		// Test
		int result = d.getBodyOffsetLeft();

		// Assert
		Assert.assertEquals(0, result);
	}
	
	@Test
	public void checkBodyTop() {
		// Test
		int result = d.getBodyOffsetTop();

		// Assert
		Assert.assertEquals(0, result);
	}

	@Test
	public void checkCreateResetButtonElement() {
		// Test
		ButtonElement e = d.createResetButtonElement();

		// Assert
		Assert.assertEquals("button", e.getTagName());
		Assert.assertEquals("reset", e.getType());
	}
}
