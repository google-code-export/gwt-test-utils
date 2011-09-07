package com.octo.gwt.test.internal.utils;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.google.gwt.dom.client.Document;

/**
 * 
 * XML parser used by gwt-test-utils. It relies on the SAX API. <strong>For
 * internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtXMLParser {

  public static Document parse(String html) throws SAXException, IOException {
    XMLReader saxReader = XmlUtils.newXMLReader();
    GwtXmlContentHandler contentHandler = new GwtXmlContentHandler();
    saxReader.setContentHandler(contentHandler);
    saxReader.parse(new InputSource(new StringReader(html)));

    return contentHandler.getParsedDocument();
  }

}
