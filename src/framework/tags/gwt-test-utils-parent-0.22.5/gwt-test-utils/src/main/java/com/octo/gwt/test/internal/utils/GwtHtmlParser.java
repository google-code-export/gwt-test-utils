package com.octo.gwt.test.internal.utils;

import java.io.StringReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test.exceptions.GwtTestPatchException;

/**
 * 
 * HTML parser used by gwt-test-utils. It relies on htmlparser. <strong>For
 * internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtHtmlParser {

  private static XMLReader PARSER;

  public static NodeList<Node> parse(String html, boolean innerHTML) {
    try {
      XMLReader saxReader = getParser();
      GwtHtmlContentHandler contentHandler = new GwtHtmlContentHandler(
          innerHTML);
      saxReader.setContentHandler(contentHandler);
      saxReader.parse(new InputSource(new StringReader(html)));
      return contentHandler.getParsedNodes();
    } catch (Exception e) {
      throw new GwtTestPatchException(
          "Error while parsing HTML '" + html + "'", e);
    }
  }

  private static XMLReader getParser() throws SAXException {
    if (PARSER == null) {
      PARSER = XMLReaderFactory.createXMLReader("org.cyberneko.html.parsers.SAXParser");

      // FIXME : this feature does not work with the NekoHTML version included
      // in
      // gwt-dev.jar (1.9.13)
      // need to use the boolean "innerHTML)
      PARSER.setFeature(
          "http://cyberneko.org/html/features/balance-tags/document-fragment",
          true);
    }

    return PARSER;

  }
}
