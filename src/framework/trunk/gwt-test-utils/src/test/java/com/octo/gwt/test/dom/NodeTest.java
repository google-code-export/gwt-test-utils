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
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.patchers.dom.NodeFactory;
import com.octo.gwt.test.internal.utils.GwtPatcherUtils;

@SuppressWarnings("deprecation")
public class NodeTest extends GwtTestTest {

  private Node n;

  @Test
  public void checkAppendChild() {
    BaseElement c0 = Document.get().createBaseElement();
    ButtonElement c1 = Document.get().createButtonElement();
    n.appendChild(c0);
    n.appendChild(c1);

    Assert.assertEquals(2, n.getChildNodes().getLength());
    Assert.assertEquals(c0, n.getChildNodes().getItem(0));
    Assert.assertEquals(c1, n.getChildNodes().getItem(1));
  }

  @Test
  public void checkAs() {
    Assert.assertEquals(n, Node.as(n));
    if (!GwtPatcherUtils.areAssertionEnabled()) {
      Assert.assertNull(Node.as(null));
    }
  }

  @Test
  public void checkCloneDeep() {
    // Setup
    AnchorElement child = Document.get().createAnchorElement();
    child.setInnerText("child inner text");
    n.appendChild(child);

    Element e = n.cast();
    e.setInnerText("text");

    // Test
    Node newNode = n.cloneNode(true);

    // Assert
    Assert.assertEquals(Node.ELEMENT_NODE, newNode.getNodeType());
    Element newElement = newNode.cast();

    Assert.assertEquals("text", newElement.getInnerText());
    Assert.assertNull("Cloned node's parent should be null",
        newNode.getParentNode());
    Assert.assertEquals("Deep cloned node should have child nodes",
        n.getChildNodes().getLength(), newNode.getChildNodes().getLength());

    Assert.assertEquals(Node.ELEMENT_NODE,
        newNode.getChildNodes().getItem(0).getNodeType());
    Element childElement = newNode.getChildNodes().getItem(0).cast();
    Assert.assertEquals("child inner text", childElement.getInnerText());

    Assert.assertEquals(Node.TEXT_NODE,
        newNode.getChildNodes().getItem(1).getNodeType());
    Text textNode = newNode.getChildNodes().getItem(1).cast();
    Assert.assertEquals("text", textNode.getData());

  }

  @Test
  public void checkCloneNotDeep() {
    // Setup
    AnchorElement child = Document.get().createAnchorElement();
    child.setInnerText("child inner text");
    n.appendChild(child);

    Element e = n.cast();
    e.setInnerText("text");

    // Test
    Node newNode = n.cloneNode(false);

    // Assert
    Assert.assertEquals(Node.ELEMENT_NODE, newNode.getNodeType());
    Element newElement = newNode.cast();

    Assert.assertEquals("text", newElement.getInnerText());

    Assert.assertNull("Cloned node's parent should be null",
        newNode.getParentNode());
    Assert.assertEquals(2, n.getChildNodes().getLength());
    Assert.assertEquals("Not deep cloned node should not have child nodes", 1,
        newNode.getChildNodes().getLength());
  }

  @Test
  public void checkGetFirstChild() {
    Assert.assertNull(n.getFirstChild());

    // Set up
    ButtonElement be0 = Document.get().createButtonElement();
    ButtonElement be1 = Document.get().createButtonElement();
    n.appendChild(be0);
    n.appendChild(be1);

    // Test & Assert
    Assert.assertEquals(be0, n.getFirstChild());
  }

  @Test
  public void checkGetLastChild() {
    Assert.assertNull(n.getLastChild());

    // Set up
    ButtonElement be0 = Document.get().createButtonElement();
    ButtonElement be1 = Document.get().createButtonElement();
    n.appendChild(be0);
    n.appendChild(be1);

    // Test & Assert
    Assert.assertEquals(be1, n.getLastChild());
  }

  @Test
  public void checkGetNextSibling() {
    Assert.assertNull(n.getNextSibling());

    // Set up
    ButtonElement be0 = Document.get().createButtonElement();
    ButtonElement be1 = Document.get().createButtonElement();
    n.appendChild(be0);
    n.appendChild(be1);

    // Test & Assert
    Assert.assertEquals(be1, be0.getNextSibling());
  }

  @Test
  public void checkGetOwnerDocument() {
    Assert.assertEquals(Document.get(), n.getOwnerDocument());
  }

  @Test
  public void checkGetParentNode() {
    Assert.assertNull(n.getParentNode());

    // Set up
    BaseElement be = Document.get().createBaseElement();
    n.appendChild(be);

    // Test and assert
    Assert.assertEquals(n, be.getParentNode());
  }

  @Test
  public void checkGetPreviousSibling() {
    Assert.assertNull(n.getPreviousSibling());

    // Set up
    ButtonElement be0 = Document.get().createButtonElement();
    ButtonElement be1 = Document.get().createButtonElement();
    n.appendChild(be0);
    n.appendChild(be1);

    // Test & Assert
    Assert.assertEquals(be0, be1.getPreviousSibling());
  }

  @Test
  public void checkHasChildNodes() {
    Assert.assertFalse("New element should not have child nodes",
        n.hasChildNodes());

    // Set up
    BaseElement be = Document.get().createBaseElement();
    n.appendChild(be);

    // Test and Assert
    Assert.assertTrue("Element should have a child node", n.hasChildNodes());
  }

  @Test
  public void checkInsertBefore() {

    // Set up
    ButtonElement be0 = Document.get().createButtonElement();
    ButtonElement be1 = Document.get().createButtonElement();
    ButtonElement be2 = Document.get().createButtonElement();
    ButtonElement be3 = Document.get().createButtonElement();
    ButtonElement be4 = Document.get().createButtonElement();
    ButtonElement be5 = Document.get().createButtonElement();
    n.appendChild(be0);
    n.appendChild(be2);

    // Test & Assert
    n.insertBefore(be1, be2);
    n.insertBefore(be3, null);
    n.insertBefore(be4, be5);

    Assert.assertEquals(be0, n.getChildNodes().getItem(0));
    Assert.assertEquals(be1, n.getChildNodes().getItem(1));
    Assert.assertEquals(be2, n.getChildNodes().getItem(2));
    Assert.assertEquals(be3, n.getChildNodes().getItem(3));
    Assert.assertEquals(be4, n.getChildNodes().getItem(4));
  }

  @Test
  public void checkIs() {
    Assert.assertFalse("null is not a DOM node", Node.is(null));
    Assert.assertFalse("NodeList is not a DOM node",
        Node.is(new OverrideNodeList<Node>()));
    Assert.assertTrue("AnchorElement is a DOM node",
        Node.is(Document.get().createAnchorElement()));
  }

  @Test
  public void checkNodeName() {
    Assert.assertEquals("#document", Document.get().getNodeName());
    Assert.assertEquals("HTML",
        Document.get().getDocumentElement().getNodeName());
    Assert.assertEquals("a", Document.get().createAnchorElement().getNodeName());
    Assert.assertEquals("#text",
        NodeFactory.createTextNode("test").getNodeName());
  }

  @Test
  public void checkNodeType() {
    Assert.assertEquals(Node.DOCUMENT_NODE, Document.get().getNodeType());
    Assert.assertEquals(Node.ELEMENT_NODE,
        Document.get().getDocumentElement().getNodeType());
    Assert.assertEquals(Node.ELEMENT_NODE,
        Document.get().createAnchorElement().getNodeType());
    Assert.assertEquals(Node.TEXT_NODE,
        NodeFactory.createTextNode("test").getNodeType());
  }

  @Test
  public void checkNodeValueOnDocument() {
    // Setup
    Node documentNode = Document.get();
    // Pre-Assert
    Assert.assertNull(documentNode.getNodeValue());

    // Test
    documentNode.setNodeValue("node");

    // Assert
    Assert.assertNull(documentNode.getNodeValue());
  }

  @Test
  public void checkNodeValueOnElement() {
    // Setup
    Node doucmentNode = Document.get().getDocumentElement();
    // Pre-Assert
    Assert.assertNull(doucmentNode.getNodeValue());

    // Test
    doucmentNode.setNodeValue("node");

    // Assert
    Assert.assertNull(doucmentNode.getNodeValue());
  }

  @Test
  public void checkNodeValueOnText() {
    // Setup
    Text textNode = Document.get().createTextNode("data");
    // Pre-Assert
    Assert.assertEquals("data", textNode.getNodeValue());

    // Test
    textNode.setNodeValue("node");

    // Assert
    Assert.assertEquals("node", textNode.getNodeValue());
    Assert.assertEquals("node", textNode.getData());
  }

  @Test
  public void checkRemoveChild() {
    // Set up
    BaseElement c0 = Document.get().createBaseElement();
    ButtonElement c1 = Document.get().createButtonElement();
    n.appendChild(c0);
    n.appendChild(c1);

    // Test
    n.removeChild(c1);

    // Assert
    Assert.assertEquals(1, n.getChildNodes().getLength());
    Assert.assertEquals(c0, n.getChildNodes().getItem(0));
  }

  @Test
  public void checkReplaceChild() {
    // Set up
    BaseElement c0 = Document.get().createBaseElement();
    ButtonElement c1 = Document.get().createButtonElement();
    AnchorElement c2 = Document.get().createAnchorElement();
    n.appendChild(c0);
    n.appendChild(c1);

    // Test
    Node replaced = n.replaceChild(c2, c1);
    Node nullReplaced = n.replaceChild(c2, c1);
    Node nullReplaced2 = n.replaceChild(c2, null);

    // Assert
    Assert.assertEquals(2, n.getChildNodes().getLength());
    Assert.assertEquals(c0, n.getChildNodes().getItem(0));
    Assert.assertEquals(c2, n.getChildNodes().getItem(1));
    Assert.assertEquals(c1, replaced);
    Assert.assertNull(nullReplaced);
    Assert.assertNull(nullReplaced2);
  }

  @Before
  public void initElement() {
    n = Document.get().createDivElement();
  }

}
