package com.octo.gwt.test.internal.utils;

import java.io.StringReader;

import org.cyberneko.html.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

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

  private static SAXParser PARSER;

  public static NodeList<Node> parse(String html) {
    try {
      XMLReader saxReader = getParser();
      GwtHtmlContentHandler contentHandler = new GwtHtmlContentHandler();
      saxReader.setContentHandler(contentHandler);
      saxReader.parse(new InputSource(new StringReader(html)));
      return contentHandler.getParsedNodes();
    } catch (Exception e) {
      throw new GwtTestPatchException(
          "Error while parsing HTML '" + html + "'", e);
    }
  }

  private static SAXParser getParser() throws SAXNotRecognizedException,
      SAXNotSupportedException {
    if (PARSER == null) {
      PARSER = new SAXParser();
      PARSER.setFeature(
          "http://cyberneko.org/html/features/balance-tags/document-fragment",
          true);
    }

    return PARSER;

  }
}
