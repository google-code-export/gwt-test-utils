package com.octo.gwt.test.dom;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.internal.patchers.dom.NodeFactory;

public class ElementTest extends GwtTestTest {

  private Element e;

  @Test
  public void checkAttribute() {
    Assert.assertEquals("", e.getAttribute("input"));
    e.setAttribute("input", "text");
    Assert.assertEquals("text", e.getAttribute("input"));
  }

  @Test
  public void checkCast() {
    AnchorElement casted = e.cast();
    Assert.assertNotNull(casted);

    try {
      ButtonElement notCasted = e.cast();
      Assert.fail("An [" + AnchorElement.class.getCanonicalName()
          + "] should not be cast in an instance of ["
          + notCasted.getClass().getCanonicalName() + "]");
    } catch (Exception e) {
      Assert.assertTrue(e instanceof ClassCastException);
    }
  }

  @Test
  public void checkClassName() {
    // Test 1
    e.setClassName("clazz");

    // Assert 1
    Assert.assertEquals("clazz", e.getClassName());
    Assert.assertEquals("clazz", e.getAttribute("class"));

    // Test 2
    e.addClassName("addon");

    // Assert 2
    Assert.assertEquals("clazz addon", e.getClassName());
    Assert.assertEquals("clazz addon", e.getAttribute("class"));

    // Test 3
    e.setAttribute("class", "override");

    // Assert 3
    Assert.assertEquals("override", e.getClassName());
    Assert.assertEquals("override", e.getAttribute("class"));
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
    Assert.assertNull("Cloned element's parent should be null",
        newNode.getParentNode());
    Assert.assertEquals(true, newNode.getPropertyBoolean("bool"));
    Assert.assertEquals("Deep cloned element should have child nodes", 1,
        newNode.getChildNodes().getLength());
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
    Assert.assertNull("Cloned element's parent should be null",
        newNode.getParentNode());
    Assert.assertEquals(true, newNode.getPropertyBoolean("bool"));
    Assert.assertEquals("Not deep cloned element should not have child nodes",
        0, newNode.getChildNodes().getLength());
    Assert.assertEquals(1, e.getChildNodes().getLength());
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
    ButtonElement be = Document.get().createPushButtonElement();
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
    Node node = Document.get().createTextNode("test");
    ButtonElement be0 = Document.get().createPushButtonElement();
    ButtonElement be1 = Document.get().createPushButtonElement();
    e.appendChild(node);
    e.appendChild(be0);
    e.appendChild(be1);

    // Test & Assert
    Assert.assertEquals(be0, e.getFirstChildElement());
  }

  @Test
  public void checkGetNextSiblingElement() {

    Assert.assertNull(e.getNextSiblingElement());

    // Set up
    ButtonElement be0 = Document.get().createPushButtonElement();
    ButtonElement be1 = Document.get().createPushButtonElement();
    e.appendChild(be0);
    e.appendChild(NodeFactory.createTextNode("test1"));
    e.appendChild(be1);
    e.appendChild(NodeFactory.createTextNode("test2"));

    // Test & Assert
    Assert.assertEquals(be1, be0.getNextSiblingElement());
    Assert.assertNull(be1.getNextSiblingElement());
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

  @Test
  public void checkGetParentElement() {
    // Set up

    Element otherParent = Document.get().createDivElement();
    Element child = Document.get().createBaseElement();
    e.appendChild(child);

    // Test and assert
    Assert.assertEquals(e, child.getParentElement());

    // Test 2
    otherParent.appendChild(child);

    // Assert 2
    Assert.assertFalse(
        "Child nodes list should be empty since the only child has been attached to another parent node",
        e.hasChildNodes());
  }

  @Test
  public void checkHashCode() {
    // Setup
    Map<Element, String> map = new HashMap<Element, String>();

    // Test
    map.put(e, "a string value");
    map.put(e, "this value should have overrided the first one");

    // Assert
    Assert.assertEquals("this value should have overrided the first one",
        map.get(e));
  }

  @Test
  public void checkId() {
    // Test 1
    e.setId("myId");

    // Assert 1
    Assert.assertEquals("myId", e.getId());
    Assert.assertEquals("myId", e.getAttribute("id"));

    // Test 2
    e.setAttribute("id", "updatedId");

    // Assert 2
    Assert.assertEquals("updatedId", e.getId());
    Assert.assertEquals("updatedId", e.getAttribute("id"));
  }

  @Test
  public void checkInnerHTML() {
    e.setInnerHTML("<h1>test</h1>");

    Assert.assertEquals("<h1>test</h1>", e.getInnerHTML());
    Assert.assertEquals(1, e.getChildCount());
    HeadingElement h1 = e.getChild(0).cast();
    Assert.assertEquals("H1", h1.getTagName());
    Assert.assertEquals("test", h1.getInnerText());
  }

  @Test
  public void checkInnerText() {
    e.setInnerText("myText");
    Assert.assertEquals("myText", e.getInnerText());
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
  public void checkLang() {
    e.setLang("myLang");
    Assert.assertEquals("myLang", e.getLang());
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
  public void checkRemoveAttribute() {
    // Set up
    e.setAttribute("test", "value");

    // Test
    e.removeAttribute("test");

    // Assert
    Assert.assertEquals("Removed attribute should return emptyString", "",
        e.getAttribute("test"));
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

  @Before
  public void initElement() {
    e = Document.get().createElement("a");
  }

}
