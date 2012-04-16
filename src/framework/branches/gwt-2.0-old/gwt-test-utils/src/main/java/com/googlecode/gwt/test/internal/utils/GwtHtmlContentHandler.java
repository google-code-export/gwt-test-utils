package com.googlecode.gwt.test.internal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;

/**
 * SAX handler for GWT DOM parsing. <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
class GwtHtmlContentHandler implements ContentHandler {

  private static final Pattern ROOT_DOCUMENT_TAG_PATTERN = Pattern.compile("^HTML|HEAD|BODY$");

  private Node currentNode;

  private final boolean innerHTML;

  private final List<Node> nodes = new ArrayList<Node>();

  public GwtHtmlContentHandler(boolean innerHTML) {
    this.innerHTML = innerHTML;
  }

  public void characters(char[] ch, int start, int length) throws SAXException {

    String string = String.valueOf(ch, start, length).replaceAll("\\u00A0", " ");

    if (string.length() > 0) {
      Text text = Document.get().createTextNode(string);

      if (currentNode != null) {
        currentNode.appendChild(text);
      } else {
        // root text node
        nodes.add(text);
      }
    }
  }

  public void endDocument() throws SAXException {
  }

  public void endElement(String nameSpaceURI, String localName, String rawName)
      throws SAXException {

    // FIXME : big hack because NekoHTML 1.9.13 (included in gwt-dev.jar) has a
    // bug with HTML fragment parsing, see :
    // http://stackoverflow.com/questions/7294525/nekohtml-sax-fragment-parsing
    if (innerHTML && ROOT_DOCUMENT_TAG_PATTERN.matcher(localName).matches()) {
      // ignore HTML/HEAD/BODY element
      return;
    }

    currentNode = currentNode.getParentNode();
  }

  public void endPrefixMapping(String prefix) throws SAXException {
  }

  public NodeList<Node> getParsedNodes() {
    return JavaScriptObjects.newNodeList(nodes);
  }

  public void ignorableWhitespace(char[] ch, int start, int end)
      throws SAXException {
  }

  public void processingInstruction(String target, String data)
      throws SAXException {
  }

  public void setDocumentLocator(Locator locator) {
  }

  public void skippedEntity(String arg0) throws SAXException {
  }

  public void startDocument() throws SAXException {
  }

  public void startElement(String nameSpaceURI, String localName,
      String rawName, Attributes attributes) throws SAXException {

    // FIXME : big hack because NekoHTML 1.9.13 (included in gwt-dev.jar) has a
    // bug with HTML fragment parsing, see :
    // http://stackoverflow.com/questions/7294525/nekohtml-sax-fragment-parsing
    if (innerHTML && ROOT_DOCUMENT_TAG_PATTERN.matcher(localName).matches()) {
      // ignore HTML/HEAD/BODY element
      return;
    }

    Element element = Document.get().createElement(localName);

    if (currentNode != null) {
      currentNode.appendChild(element);
    } else {
      // root node
      nodes.add(element);
    }
    currentNode = element;

    for (int index = 0; index < attributes.getLength(); index++) {
      String attrName = attributes.getLocalName(index);
      String attrValue = attributes.getValue(index);

      if ("style".equalsIgnoreCase(attrName)) {
        StyleUtils.overrideStyle(element.getStyle(), attrValue);
      } else if ("class".equalsIgnoreCase(attrName)) {
        element.setClassName(attrValue);
      } else {
        element.setAttribute(attrName, attrValue);

      }
    }
  }

  public void startPrefixMapping(String prefix, String URI) throws SAXException {
  }
}
