package com.octo.gwt.test.internal.uibinder;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.octo.gwt.test.internal.uibinder.objects.UiBinderTag;
import com.octo.gwt.test.internal.uibinder.objects.UiBinderTagFactory;
import com.octo.gwt.test.utils.FastStack;

public class UiXmlContentHandler implements ContentHandler {

  private final UiBinderTagFactory factory;
  private Object rootComponent;
  private final FastStack<UiBinderTag> tags = new FastStack<UiBinderTag>();

  public UiXmlContentHandler(UiBinderTagFactory factory) {
    this.factory = factory;
  }

  public void characters(char[] ch, int start, int end) throws SAXException {
    if (end > start) {
      tags.get(tags.size() - 1).appendText(new String(ch, start, end));
    }
  }

  public void endDocument() throws SAXException {
  }

  public void endElement(String nameSpaceURI, String localName, String rawName)
      throws SAXException {

    // skip root tag
    if (!UiBinderUtils.isUiBinderTag(nameSpaceURI, localName)) {
      UiBinderTag tag = tags.pop();

      if (tags.size() == 0) {
        // parsing is finished, this is the root component
        rootComponent = tag.complete();
      } else {
        // add to its parent
        tags.get(tags.size() - 1).addTag(tag);
      }
    }

  }

  public void endPrefixMapping(String prefix) throws SAXException {
  }

  public Object getRootComponent() {
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

  }

  public void startElement(String nameSpaceURI, String localName,
      String rawName, Attributes attributes) throws SAXException {

    // skip UiBinder element
    if (!UiBinderUtils.isUiBinderTag(nameSpaceURI, localName)) {
      tags.push(factory.createUiBinderTag(nameSpaceURI, localName, attributes));
    }
  }

  public void startPrefixMapping(String prefix, String URI) throws SAXException {
  }

}
