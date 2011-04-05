package com.octo.gwt.test.internal;

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
import com.octo.gwt.test.internal.patchers.dom.NodeFactory;

public class GwtXMLParser {

  private static class GwtContentHandler implements ContentHandler {

    private Node currentNode;
    private Document document;

    private GwtContentHandler() {
    }

    public void characters(char[] ch, int start, int end) throws SAXException {
      System.out.println("#PCDATA : " + new String(ch, start, end));
      Text text = Document.get().createTextNode(new String(ch, start, end));
      currentNode.appendChild(text);
    }

    public void endDocument() throws SAXException {
      System.out.println("Fin de l'analyse du document");
    }

    public void endElement(String nameSpaceURI, String localName, String rawName)
        throws SAXException {
      currentNode = currentNode.getParentNode();
    }

    public void endPrefixMapping(String prefix) throws SAXException {
      System.out.println("Fin de traitement de l'espace de nommage : " + prefix);
    }

    public void ignorableWhitespace(char[] ch, int start, int end)
        throws SAXException {
      System.out.println("espaces inutiles rencontres : ..."
          + new String(ch, start, end) + "...");
    }

    public void processingInstruction(String target, String data)
        throws SAXException {
      System.out.println("Instruction de fonctionnement : " + target);
      System.out.println("  dont les arguments sont : " + data);
    }

    public void setDocumentLocator(Locator locator) {
    }

    public void skippedEntity(String arg0) throws SAXException {
    }

    public void startDocument() throws SAXException {
      document = NodeFactory.createDocument();
      currentNode = document;
    }

    public void startElement(String nameSpaceURI, String localName,
        String rawName, Attributes attributs) throws SAXException {

      Element element = Document.get().createElement(localName);

      currentNode.appendChild(element);

      currentNode = element;
      System.out.println("Ouverture de la balise : " + localName);

      if (!"".equals(nameSpaceURI)) { // espace de nommage particulier
        System.out.println("  appartenant a l'espace de nom : " + nameSpaceURI);
      }
    }

    public void startPrefixMapping(String prefix, String URI)
        throws SAXException {
      System.out.println("Traitement de l'espace de nommage : " + URI
          + ", prefixe choisi : " + prefix);
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
