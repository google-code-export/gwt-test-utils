package com.octo.gwt.test.internal.uibinder;

import java.util.LinkedHashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class UiXmlContentHandler implements ContentHandler {

  private static final String UIBINDER_PAQUAGE = "com.google.gwt.uibinder";

  private Node currentNode;
  private final Object owner;

  public UiXmlContentHandler(Object root, Object owner) {
    this.currentNode = (Node) root;
    this.owner = owner;
  }

  public void characters(char[] ch, int start, int end) throws SAXException {
    if (end > start) {
      Text text = Document.get().createTextNode(new String(ch, start, end));
      currentNode.appendChild(text);
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

  }

  public void startElement(String nameSpaceURI, String localName,
      String rawName, Attributes attributes) throws SAXException {

    Element element = Document.get().createElement(localName);

    JavaScriptObjects.getJsoProperties(element).put(
        JsoProperties.NODE_NAMESPACE_URI, nameSpaceURI);

    currentNode.appendChild(element);
    currentNode = element;

    Set<String> attrs = new LinkedHashSet<String>();
    JavaScriptObjects.getJsoProperties(element).put(JsoProperties.XML_ATTR_SET,
        attrs);

    for (int index = 0; index < attributes.getLength(); index++) {
      String attrName = attributes.getLocalName(index);
      String attrValue = attributes.getValue(index);
      String uri = attributes.getURI(index);
      if ("field".equals(attrName)
          && UIBINDER_PAQUAGE.equals(uri.substring(uri.lastIndexOf(':') + 1))) {
        // inject @UiField
        GwtReflectionUtils.setPrivateFieldValue(owner, attrValue, currentNode);
      } else {
        // normal attribute to be added
        element.setAttribute(attrName, attrValue);
        attrs.add(attrName);
      }

    }
  }

  public void startPrefixMapping(String prefix, String URI) throws SAXException {
  }

}
