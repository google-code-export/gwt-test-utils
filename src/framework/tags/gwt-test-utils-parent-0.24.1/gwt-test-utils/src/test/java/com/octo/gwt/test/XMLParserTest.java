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

import com.google.gwt.xml.client.Attr;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

public class XMLParserTest extends GwtTestTest {

  @Test
  public void checkParse() throws Exception {
    // Setup
    String xmlContent = convertXMLFileToString("/someXML.xml");

    // Test
    Document document = XMLParser.parse(xmlContent);

    // Asserts
    Element documentElement = document.getDocumentElement();
    Assert.assertEquals("beans", documentElement.getTagName());

    Element testBean = document.getElementById("testBean");
    NodeList beans = document.getElementsByTagName("bean");

    Assert.assertEquals(2, beans.getLength());
    Assert.assertEquals("testBean",
        beans.item(0).getAttributes().getNamedItem("id").getNodeValue());
    Assert.assertEquals("bean", testBean.getTagName());
    Assert.assertEquals("bean", testBean.getNodeName());
    Assert.assertEquals("http://www.springframework.org/schema/beans",
        testBean.getNamespaceURI());
    Assert.assertEquals("org.springframework.beans.TestBean",
        testBean.getAttribute("class"));
    Assert.assertTrue(testBean.hasAttribute("class"));
    Assert.assertFalse(testBean.hasAttribute("fooAttr"));
    Attr classAttr = testBean.getAttributeNode("class");
    Assert.assertEquals("class", classAttr.getName());
    Assert.assertEquals("org.springframework.beans.TestBean",
        classAttr.getValue());
    Assert.assertEquals("http://www.springframework.org/schema/beans",
        classAttr.getNamespaceURI());
    Assert.assertEquals("", classAttr.getNodeName());
    // CDATA attribute
    Element ageProperty = (Element) testBean.getChildNodes().item(0);
    Assert.assertEquals(1, ageProperty.getAttributes().getLength());
    Assert.assertEquals("age", ageProperty.getAttribute("name"));
    Assert.assertEquals("<10>", ageProperty.getNodeValue());

    // "spouse" child bean assertions
    NamedNodeMap innerBeanAgePropertyAttributes = beans.item(1).getChildNodes().item(
        0).getAttributes();
    Assert.assertEquals("age",
        innerBeanAgePropertyAttributes.getNamedItem("name").getNodeValue());
    Assert.assertEquals("11",
        innerBeanAgePropertyAttributes.getNamedItem("value").getNodeValue());

    // bean from "util" namespace
    Element name = document.getElementById("name");
    Assert.assertEquals("property-path", name.getTagName());
    Assert.assertEquals("property-path", name.getNodeName());
    Assert.assertEquals("http://www.springframework.org/schema/util",
        name.getNamespaceURI());

  }

  @Test
  public void checkParseSimple() {
    // Setup
    String simpleXML = "<tags><tag>value</tag></tags>";

    // Test
    Document doc = XMLParser.parse(simpleXML);

    // Asserts
    NodeList tags = doc.getElementsByTagName("tag");
    Text text = (Text) tags.item(0).getChildNodes().item(0);
    Assert.assertEquals("value", text.getData());

  }

  @Test
  public void checkRemoveWhitespace() throws Exception {
    // Setup
    Document document = XMLParser.createDocument();
    Element child = document.createElement("child");
    child.setNodeValue("     ");
    document.appendChild(child);
    Element child2 = document.createElement("child");
    child2.appendChild(document.createCDATASection("    "));
    document.appendChild(child2);

    // Pre-Assert : empty TextNode should exists
    Assert.assertEquals(1, child.getChildNodes().getLength());
    Assert.assertEquals(1, child2.getChildNodes().getLength());

    // Test
    XMLParser.removeWhitespace(document);

    // Asserts
    Assert.assertEquals(0, child.getChildNodes().getLength());
    // empty cdata is not removed
    Assert.assertEquals(1, child2.getChildNodes().getLength());

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
