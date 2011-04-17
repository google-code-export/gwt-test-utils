package com.octo.gwt.test.internal.uibinder;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.octo.gwt.test.internal.uibinder.objects.UiBinderComponentBuilder;

public class UiXmlContentHandler<T> implements ContentHandler {

  private UiBinderComponentBuilder<T> builder;
  private final Object owner;

  private T rootComponent;
  private final Class<T> rootComponentClass;

  public UiXmlContentHandler(Class<T> rootComponentClass, Object owner) {
    this.rootComponentClass = rootComponentClass;
    this.owner = owner;
  }

  public void characters(char[] ch, int start, int end) throws SAXException {
    this.builder.appendText(ch, start, end);
  }

  public void endDocument() throws SAXException {
    this.rootComponent = this.builder.build();
  }

  public void endElement(String nameSpaceURI, String localName, String rawName)
      throws SAXException {
    this.builder.endTag(nameSpaceURI, localName);
  }

  public void endPrefixMapping(String prefix) throws SAXException {
  }

  public T getRootComponent() {
    return rootComponent;
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
    this.builder = UiBinderComponentBuilder.create(this.rootComponentClass,
        this.owner);
  }

  public void startElement(String nameSpaceURI, String localName,
      String rawName, Attributes attributes) throws SAXException {
    this.builder.startTag(nameSpaceURI, localName, attributes);
  }

  public void startPrefixMapping(String prefix, String URI) throws SAXException {

  }

}
