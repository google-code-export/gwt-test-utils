package com.octo.gwt.test.dom;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.AbstractGwtTest;

public class DocumentTest extends AbstractGwtTest {

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

	@Test
	public void checkSetScrollLeft() {
		Assert.assertEquals(0, d.getScrollLeft());
		d.setScrollLeft(3);

		Assert.assertEquals(3, d.getScrollLeft());
	}

	@Test
	public void checkSetScrollTop() {
		Assert.assertEquals(0, d.getScrollTop());
		d.setScrollTop(3);

		Assert.assertEquals(3, d.getScrollTop());
	}

	@Test
	public void checkGetDocumentElement() {
		// Test
		Element e = d.getDocumentElement();

		// Assert
		Assert.assertEquals("HTML", e.getTagName());
		Assert.assertEquals("HTML", e.getNodeName());
		Assert.assertEquals(Node.ELEMENT_NODE, e.getNodeType());
		Assert.assertEquals(d.getChild(0), e);
	}

	@Test
	public void checkCreateTextNode() {
		// Test
		String data = "myData";
		Text text = d.createTextNode(data);

		// Assert
		Assert.assertEquals(Node.TEXT_NODE, text.getNodeType());
		Assert.assertEquals(data, text.getData());
	}

	@Test
	public void checkGetDomain() {
		Assert.assertNull(d.getDomain());
	}

	@Test
	public void checkGetReferrer() {
		Assert.assertEquals("", d.getReferrer());
	}

	@Test
	public void checkGetElementByIdNotFound() {
		// Setup
		AnchorElement a1 = Document.get().createAnchorElement();
		AnchorElement a2 = Document.get().createAnchorElement();
		AnchorElement a3 = Document.get().createAnchorElement();
		DivElement d1 = Document.get().createDivElement();
		d.appendChild(a1);
		d.appendChild(a1);
		d.appendChild(a2);
		a2.appendChild(a3);
		d.appendChild(d1);

		// Test
		Element result = d.getElementById("myId");

		// Assert
		Assert.assertNull(result);
	}

	@Test
	public void checkGetElementByIdFound() {
		// Setup
		AnchorElement a1 = Document.get().createAnchorElement();
		AnchorElement a2 = Document.get().createAnchorElement();
		AnchorElement a3 = Document.get().createAnchorElement();
		a3.setId("myId");
		DivElement d1 = Document.get().createDivElement();
		d.appendChild(a1);
		d.appendChild(a1);
		d.appendChild(a2);
		a2.appendChild(a3);
		d.appendChild(d1);

		// Test
		Element result = d.getElementById("myId");

		// Assert
		Assert.assertEquals(a3, result);
	}

	@Test
	public void checkGetElementByTagName() {
		// Setup
		AnchorElement a1 = Document.get().createAnchorElement();
		AnchorElement a2 = Document.get().createAnchorElement();
		AnchorElement a3 = Document.get().createAnchorElement();
		DivElement d1 = Document.get().createDivElement();
		d.appendChild(a1);
		d.appendChild(a1);
		d.appendChild(a2);
		a2.appendChild(a3);
		d.appendChild(d1);

		// Test
		NodeList<Element> nodes = d.getElementsByTagName("a");

		// Assert
		Assert.assertEquals(3, nodes.getLength());
		Assert.assertEquals(a1, nodes.getItem(0));
		Assert.assertEquals(a2, nodes.getItem(1));
		Assert.assertEquals(a3, nodes.getItem(2));
	}
}
