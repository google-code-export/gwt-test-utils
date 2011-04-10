package com.octo.gwt.test;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

public class XMLParserTest extends GwtTestTest {

  @Test
  public void checkParse() throws Exception {
    // Setup
    String xmlContent = convertXMLFileToString("/someXML.xml");

    // Test
    Document document = XMLParser.parse(xmlContent);

    // Asserts
    Element element = document.getElementById("testBean");
    Assert.assertEquals("bean", element.getTagName());
    Assert.assertEquals("http://www.springframework.org/schema/beans",
        element.getNamespaceURI());
    Assert.assertEquals("org.springframework.beans.TestBean",
        element.getAttribute("class"));
    // Assert.assertEquals("org.springframework.beans.TestBean",
    // element.getAttributeNode("class").getValue());
  }

  private String convertXMLFileToString(String fileName) {
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      InputStream inputStream = this.getClass().getResourceAsStream(fileName);
      org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(
          inputStream);
      StringWriter stw = new StringWriter();
      Transformer serializer = TransformerFactory.newInstance().newTransformer();
      serializer.transform(new DOMSource(doc), new StreamResult(stw));
      return stw.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
