package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gwt.dom.client.BRElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.GwtHtmlParser;

public class GwtHtmlParserTest extends GwtTestTest {

	// @Test
	// public void checkFormat() throws Exception {
	// // Setup
	// DivElement root = Document.get().createDivElement();
	// root.setId("root");
	// DivElement div0 = Document.get().createDivElement();
	// div0.setId("div0");
	// div0.setClassName("div0Class");
	// div0.getStyle().setBackgroundColor("red");
	// div0.getStyle().setBorderWidth(10.1, Unit.EM);
	// root.appendChild(div0);
	//
	// DivElement div1 = Document.get().createDivElement();
	// SpanElement span = Document.get().createSpanElement();
	// span.setInnerText("spanText àéè");
	// span.setAttribute("customAttr", "customValue");
	// span.getStyle().setFloat(Float.RIGHT);
	// div1.appendChild(span);
	// div1.appendChild(Document.get().createBRElement());
	// div1.appendChild(Document.get().createCaptionElement());
	// root.appendChild(div1);
	//
	// String expectedHtml =
	// "<div id=\"div0\" class=\"div0Class\" style=\"background-color: red; border-top-width: 10.1em; border-right-width: 10.1em; border-bottom-width: 10.1em; border-left-width: 10.1em; \"></div><div><span customattr=\"customValue\" style=\"float: right; \">spanText àéè</span><br><caption></caption></div>";
	//
	// // Test
	// String html = GwtHtmlParser.format(root);
	//
	// // Assert
	// Assert.assertEquals(expectedHtml, html);
	// }

	@Ignore
	@Test
	public void checkParse() throws Exception {
		// Setup
		String html = "<div id=\"parent0\"></div><div id=\"parent1\"><div id=\"child0\"><span class=\"spanClass\" >test</span></div><BR><DIV id=\"child2\" style=\"color:red; font-style:italic; font-weight:bold; font-family:Arial\"></div>";

		// Test
		NodeList<Node> nodes = GwtHtmlParser.parse(html);

		// Assert
		Assert.assertEquals(2, nodes.getLength());
		DivElement parent0 = (DivElement) nodes.getItem(0);
		Assert.assertEquals("parent0", parent0.getId());
		Assert.assertEquals(0, parent0.getChildCount());

		DivElement parent1 = (DivElement) nodes.getItem(1);
		Assert.assertEquals("parent1", parent1.getId());
		Assert.assertEquals(3, parent1.getChildCount());

		DivElement child0 = (DivElement) parent1.getChild(0);
		Assert.assertEquals("child0", child0.getId());
		Assert.assertEquals(1, child0.getChildCount());

		SpanElement span = (SpanElement) child0.getChild(0);
		Assert.assertEquals("", span.getId());
		Assert.assertEquals("spanClass", span.getClassName());
		Assert.assertEquals(1, span.getChildCount());
		Assert.assertEquals(Node.TEXT_NODE, span.getChild(0).getNodeType());
		Text text = span.getChild(0).cast();
		Assert.assertEquals("test", text.getData());
		Assert.assertEquals("test", span.getInnerText());

		BRElement br = (BRElement) parent1.getChild(1);
		Assert.assertEquals("", br.getId());

		DivElement child2 = (DivElement) parent1.getChild(2);
		Assert.assertEquals("child2", child2.getId());
		Assert.assertEquals(0, child2.getChildCount());
		Style style = child2.getStyle();
		Assert.assertEquals("red", style.getColor());
	}
}
