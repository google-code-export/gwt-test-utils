package com.octo.gwt.test.internal.utils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.tools.ant.filters.StringInputStream;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;

/**
 * 
 * XML parser used by gwt-test-utils. It relies on the SAX API. <strong>For
 * internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtXMLParser {

  private static class GwtContentHandler implements ContentHandler {

    private Node currentNode;
    private Document document;

    private GwtContentHandler() {
    }

    public void characters(char[] ch, int start, int length)
        throws SAXException {

      String text = String.copyValueOf(ch, start, length).trim();

      if (text.length() > 0) {
        currentNode.appendChild(Document.get().createTextNode(text));
      }
    }

    public void endDocument() throws SAXException {
    }

    public void endElement(String nameSpaceURI, String localName, String rawName)
        throws SAXException {
      currentNode = currentNode.getParentNode();
    }

    public void endPrefixMapping(String prefix) throws SAXException {
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
      document = JavaScriptObjects.newObject(Document.class);
      currentNode = document;
    }

    public void startElement(String nameSpaceURI, String localName,
        String rawName, Attributes attributes) throws SAXException {

      Element element = Document.get().createElement(localName);

      JavaScriptObjects.setProperty(element, JsoProperties.NODE_NAMESPACE_URI,
          nameSpaceURI);

      currentNode.appendChild(element);
      currentNode = element;

      Set<String> attrs = new LinkedHashSet<String>();
      JavaScriptObjects.setProperty(element, JsoProperties.XML_ATTR_SET, attrs);

      for (int index = 0; index < attributes.getLength(); index++) {
        String attrName = attributes.getLocalName(index);
        String attrValue = attributes.getValue(index);
        element.setAttribute(attrName, attrValue);
        attrs.add(attrName);
      }
    }

    public void startPrefixMapping(String prefix, String URI)
        throws SAXException {
    }

  }

  public static Document parse(String html) throws SAXException, IOException {
    XMLReader saxReader = XmlUtils.newXMLReader();
    GwtContentHandler contentHandler = new GwtContentHandler();
    saxReader.setContentHandler(contentHandler);
    saxReader.parse(new InputSource(new StringInputStream(html, "UTF-8")));

    return contentHandler.document;
  }

}
