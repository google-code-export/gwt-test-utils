package com.octo.gwt.test.internal.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.octo.gwt.test.exceptions.GwtTestConfigurationException;

/**
 * 
 * <strong>For internal use only</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class XmlUtils {

  private static DocumentBuilderFactory documentBuilderFactory;

  static {

    documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setValidating(false);
    documentBuilderFactory.setNamespaceAware(false);
  }

  public static DocumentBuilder newDocumentBuilder() {
    try {
      return documentBuilderFactory.newDocumentBuilder();
    } catch (Exception e) {
      // should never happen
      throw new GwtTestConfigurationException(
          "Error while creating a DocumentBuilder", e);
    }
  }

  public static XMLReader newXMLReader() {
    try {
      XMLReader saxReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
      saxReader.setFeature("http://xml.org/sax/features/validation", false);
      saxReader.setFeature(
          "http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
          false);
      saxReader.setFeature(
          "http://apache.org/xml/features/nonvalidating/load-external-dtd",
          false);
      return saxReader;

    } catch (Exception e) {
      // should never happen..
      throw new GwtTestConfigurationException(
          "Error while creating a XMLReader", e);
    }

  }

  private XmlUtils() {

  }
}
