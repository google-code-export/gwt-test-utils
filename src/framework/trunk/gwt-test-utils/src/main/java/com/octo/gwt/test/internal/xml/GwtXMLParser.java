package com.octo.gwt.test.internal.xml;

import java.io.IOException;

import org.apache.tools.ant.filters.StringInputStream;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;

public class GwtXMLParser {

  private static class GwtContentHandler implements ContentHandler {

    private Node currentNode;
    private Document document;

    private GwtContentHandler() {
    }

    public void characters(char[] ch, int start, int end) throws SAXException {
      Text text = Document.get().createTextNode(new String(ch, start, end));
      currentNode.appendChild(text);
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

      JavaScriptObjects.getJsoProperties(element).put(
          JsoProperties.NODE_NAMESPACE_URI, nameSpaceURI);

      currentNode.appendChild(element);
      currentNode = element;

      for (int index = 0; index < attributes.getLength(); index++) {
        String attrName = attributes.getLocalName(index);
        String attrValue = attributes.getValue(index);
        element.setAttribute(attrName, attrValue);
      }
    }

    public void startPrefixMapping(String prefix, String URI)
        throws SAXException {
    }

  }

  public static Document parse(String html) throws SAXException, IOException {
    XMLReader saxReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
    GwtContentHandler contentHandler = new GwtContentHandler();
    saxReader.setContentHandler(contentHandler);
    saxReader.parse(new InputSource(new StringInputStream(html, "UTF-8")));

    return contentHandler.document;
  }

}
