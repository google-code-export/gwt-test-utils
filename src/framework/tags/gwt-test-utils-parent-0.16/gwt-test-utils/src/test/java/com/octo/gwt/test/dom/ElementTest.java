package com.octo.gwt.test.dom;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.BaseElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.internal.patcher.dom.NodeFactory;

@SuppressWarnings("deprecation")
public class ElementTest extends AbstractGwtTest {

	private Element e;

	@Before
	public void initElement() {
		e = Document.get().createElement("a");
	}

	@Test
	public void checkCast() {
		AnchorElement casted = e.cast();
		Assert.assertNotNull(casted);

		try {
			ButtonElement notCasted = e.cast();
			Assert.fail("An [" + AnchorElement.class.getCanonicalName() + "] should not be cast in an instance of ["
					+ notCasted.getClass().getCanonicalName() + "]");
		} catch (Exception e) {
			Assert.assertTrue(e instanceof ClassCastException);
		}
	}

	@Test
	public void checkCloneDeep() {
		e.setTitle("title");
		e.setPropertyBoolean("bool", true);

		AnchorElement child = Document.get().createAnchorElement();
		child.setTitle("child");
		e.appendChild(child);

		AnchorElement newNode = (AnchorElement) e.cloneNode(true);
		Assert.assertEquals("title", newNode.getTitle());
		Assert.assertNull("Cloned element's parent should be null", newNode.getParentNode());
		Assert.assertEquals(true, newNode.getPropertyBoolean("bool"));
		Assert.assertEquals("Deep cloned element should have child nodes", 1, newNode.getChildNodes().getLength());
		Assert.assertTrue(child != newNode.getChildNodes().getItem(0));
		Assert.assertEquals(1, e.getChildNodes().getLength());
	}

	@Test
	public void checkCloneNotDeep() {
		e.setTitle("title");
		e.setPropertyBoolean("bool", true);

		AnchorElement child = Document.get().createAnchorElement();
		child.setTitle("child");
		e.appendChild(child);

		AnchorElement newNode = (AnchorElement) e.cloneNode(false);
		Assert.assertEquals("title", newNode.getTitle());
		Assert.assertNull("Cloned element's parent should be null", newNode.getParentNode());
		Assert.assertEquals(true, newNode.getPropertyBoolean("bool"));
		Assert.assertEquals("Not deep cloned element should not have child nodes", 0, newNode.getChildNodes().getLength());
		Assert.assertEquals(1, e.getChildNodes().getLength());
	}

	@Test
	public void checkAttribute() {
		Assert.assertNull(e.getAttribute("input"));
		e.setAttribute("input", "text");
		Assert.assertEquals("text", e.getAttribute("input"));
	}

	@Test
	public void checkClassName() {
		e.setClassName("clazz");
		Assert.assertEquals("clazz", e.getClassName());
	}

	@Test
	public void checkDir() {
		e.setDir("dir");
		Assert.assertEquals("dir", e.getDir());
	}

	@Test
	public void checkGetElementByTagName() {
		// Set up
		AnchorElement ae0 = Document.get().createAnchorElement();
		AnchorElement ae1 = Document.get().createAnchorElement();
		ButtonElement be = Document.get().createButtonElement();
		e.appendChild(ae0);
		e.appendChild(ae1);
		e.appendChild(be);

		// Tests
		NodeList<Element> anchorList = e.getElementsByTagName("a");
		NodeList<Element> buttonList = e.getElementsByTagName("button");
		NodeList<Element> allList = e.getElementsByTagName("*");

		// Asserts
		Assert.assertEquals(2, anchorList.getLength());
		Assert.assertEquals(ae0, anchorList.getItem(0));
		Assert.assertEquals(ae1, anchorList.getItem(1));

		Assert.assertEquals(1, buttonList.getLength());
		Assert.assertEquals(be, buttonList.getItem(0));

		Assert.assertEquals(3, allList.getLength());
		Assert.assertEquals(ae0, allList.getItem(0));
		Assert.assertEquals(ae1, allList.getItem(1));
		Assert.assertEquals(be, allList.getItem(2));
	}

	@Test
	public void checkGetFirstChildElement() {
		Assert.assertNull(e.getFirstChildElement());

		// Set up
		Node node = NodeFactory.createNode();
		ButtonElement be0 = Document.get().createButtonElement();
		ButtonElement be1 = Document.get().createButtonElement();
		e.appendChild(node);
		e.appendChild(be0);
		e.appendChild(be1);

		// Test & Assert
		Assert.assertEquals(be0, e.getFirstChildElement());
	}

	@Test
	public void checkId() {
		e.setId("myId");
		Assert.assertEquals("myId", e.getId());
	}

	@Test
	public void checkInnerHTML() {
		e.setInnerHTML("<h1>test</h1>");
		Assert.assertEquals("<h1>test</h1>", e.getInnerHTML());
	}

	@Test
	public void checkInnerText() {
		e.setInnerText("myText");
		Assert.assertEquals("myText", e.getInnerText());
	}

	@Test
	public void checkLang() {
		e.setLang("myLang");
		Assert.assertEquals("myLang", e.getLang());
	}

	@Test
	public void checkGetNextSiblingElement() {

		Assert.assertNull(e.getNextSiblingElement());

		// Set up
		ButtonElement be0 = Document.get().createButtonElement();
		ButtonElement be1 = Document.get().createButtonElement();
		e.appendChild(be0);
		e.appendChild(NodeFactory.createTextNode("test1"));
		e.appendChild(be1);
		e.appendChild(NodeFactory.createTextNode("test2"));

		// Test & Assert
		Assert.assertEquals(be1, be0.getNextSiblingElement());
		Assert.assertNull(be1.getNextSiblingElement());
	}

	@Test
	public void checkGetParentElement() {
		//Set up
		BaseElement be = Document.get().createBaseElement();
		e.appendChild(be);

		//Test and assert
		Assert.assertEquals(e, be.getParentElement());

		//Set up
		Node node = NodeFactory.createNode();
		node.appendChild(be);

		Assert.assertNull(be.getParentElement());

		Assert.assertFalse("Child nodes list should be empty since the only child has been attached to another parent node", e.hasChildNodes());
	}

	@Test
	public void checkPropertyBoolean() {
		e.setPropertyBoolean("prop", true);
		Assert.assertTrue(e.getPropertyBoolean("prop"));

		e.setPropertyBoolean("prop", false);
		Assert.assertFalse(e.getPropertyBoolean("prop"));
	}

	@Test
	public void checkPropertyDouble() {
		e.setPropertyDouble("prop", 23);
		Assert.assertEquals(new Double(23), (Double) e.getPropertyDouble("prop"));
	}

	@Test
	public void checkPropertyInt() {
		e.setPropertyInt("prop", 2);
		Assert.assertEquals(new Integer(2), (Integer) e.getPropertyInt("prop"));
	}

	@Test
	public void checkPropertyString() {
		e.setPropertyString("prop", "test");
		Assert.assertEquals("test", e.getPropertyString("prop"));
	}

	@Test
	public void checkScrollLeft() {
		e.setScrollLeft(3);
		Assert.assertEquals(3, e.getScrollLeft());
	}

	@Test
	public void checkScrollTop() {
		e.setScrollTop(3);
		Assert.assertEquals(3, e.getScrollTop());
	}

	@Test
	public void checkStyle() {
		e.getStyle().setProperty("test", "value");
		Assert.assertEquals("value", e.getStyle().getProperty("test"));
	}

	@Test
	public void checkTagName() {
		Assert.assertEquals("a", e.getTagName());
	}

	@Test
	public void checkTitle() {
		e.setTitle("myTitle");
		Assert.assertEquals("myTitle", e.getTitle());
	}

	@Test
	public void checkIsOrHasChild() {
		// Set up
		AnchorElement child = Document.get().createAnchorElement();
		e.appendChild(child);
		AnchorElement notAChild = Document.get().createAnchorElement();

		// Test & Assert
		Assert.assertTrue(e.isOrHasChild(e));
		Assert.assertTrue(e.isOrHasChild(child));
		Assert.assertFalse(e.isOrHasChild(notAChild));
	}

	@Test
	public void checkRemoveAttribute() {
		// Set up
		e.setAttribute("test", "value");

		// Test
		e.removeAttribute("test");

		// Assert
		Assert.assertNull("Removed attribute should return null", e.getAttribute("test"));
	}

	@Test
	public void checkGetOffset() {
		Element parent = Document.get().createElement("a");
		parent.appendChild(e);
		Assert.assertEquals(0, e.getOffsetHeight());
		Assert.assertEquals(0, e.getOffsetLeft());
		Assert.assertEquals(0, e.getOffsetTop());
		Assert.assertEquals(0, e.getOffsetWidth());
		Assert.assertEquals(parent, e.getOffsetParent());
	}

}
