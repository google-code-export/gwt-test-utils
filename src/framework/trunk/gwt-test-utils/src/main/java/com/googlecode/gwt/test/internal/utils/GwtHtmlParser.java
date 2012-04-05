package com.googlecode.gwt.test.internal.utils;

import java.io.StringReader;
import java.util.Collections;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.cyberneko.html.filters.DefaultFilter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.googlecode.gwt.test.exceptions.GwtTestPatchException;

/**
 * 
 * HTML parser used by gwt-test-utils. It relies on htmlparser. <strong>For
 * internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtHtmlParser {

  /**
   * Filter which keep "&nbsp;" and "&nbsp;" strings instead of converting them
   * in a ' ' character.
   * 
   */
  private static class NbspRemover extends DefaultFilter {

    private static final String NBSP_ENTITY_NAME = "nbsp";

    boolean inNbspEntityRef;

    XMLString nbspXMLString;

    private NbspRemover() {
      nbspXMLString = new XMLString();
      char[] c = {'&', 'n', 'b', 's', 'p', ';'};
      nbspXMLString.setValues(c, 0, 6);
    }

    @Override
    public void characters(XMLString text, Augmentations augs)
        throws XNIException {

      if (!inNbspEntityRef) {
        super.characters(text, augs);
      }
    }

    @Override
    public void endGeneralEntity(String name, Augmentations augs)
        throws XNIException {

      inNbspEntityRef = false;
    }

    @Override
    public void startDocument(XMLLocator locator, String encoding,
        Augmentations augs) throws XNIException {

      super.startDocument(locator, encoding, augs);
      inNbspEntityRef = false;
    }

    @Override
    public void startGeneralEntity(String name, XMLResourceIdentifier id,
        String encoding, Augmentations augs) throws XNIException {

      if (NBSP_ENTITY_NAME.equals(name)) {
        inNbspEntityRef = true;
        super.characters(nbspXMLString, augs);
      } else {
        super.startGeneralEntity(name, id, encoding, augs);
      }
    }
  }

  private static XMLReader PARSER;

  public static NodeList<Node> parse(String html, boolean innerHTML) {
    if (html == null || html.trim().length() == 0) {
      return JavaScriptObjects.newNodeList(Collections.<Node> emptyList());
    }

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

      // FIXME : this feature does not work with the NekoHTML version
      // included
      // in
      // gwt-dev.jar (1.9.13)
      // need to use the boolean "innerHTML)
      PARSER.setFeature(
          "http://cyberneko.org/html/features/balance-tags/document-fragment",
          true);

      PARSER.setFeature(
          "http://cyberneko.org/html/features/scanner/notify-builtin-refs",
          true);

      PARSER.setProperty(
          "http://cyberneko.org/html/properties/default-encoding", "UTF-8");

      XMLDocumentFilter[] filters = {new NbspRemover()};

      PARSER.setProperty("http://cyberneko.org/html/properties/filters",
          filters);
    }

    return PARSER;

  }

}
