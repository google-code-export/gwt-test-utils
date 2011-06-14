package com.octo.gwt.test;

import java.io.InputStream;
import java.io.StringWriter;

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
import com.octo.gwt.test.internal.utils.XmlUtils;

public class XMLParserTest extends GwtTestTest {

  @Test
  public void checkParse() throws Exception {
    // Arrange
    String xmlContent = convertXMLFileToString("/someXML.xml");

    // Act
    Document document = XMLParser.parse(xmlContent);

    // Assert
    Element documentElement = document.getDocumentElement();
    Assert.assertEquals("beans", documentElement.getTagName());

    Element beans = (Element) document.getFirstChild();
    Assert.assertEquals("beans", beans.getTagName());
    Assert.assertNull(beans.getNextSibling());
    Assert.assertNull(beans.getPreviousSibling());

    Element testBean = document.getElementById("testBean");
    NodeList beanList = document.getElementsByTagName("bean");

    Assert.assertEquals(2, beanList.getLength());
    Assert.assertEquals("testBean",
        beanList.item(0).getAttributes().getNamedItem("id").getNodeValue());
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
    Assert.assertEquals("class", classAttr.getNodeName());
    // CDATA attribute
    Element ageProperty = (Element) testBean.getChildNodes().item(0);
    Assert.assertEquals(1, ageProperty.getAttributes().getLength());
    Assert.assertEquals("age", ageProperty.getAttribute("name"));
    Assert.assertEquals("<10>", ageProperty.getNodeValue());
    // TODO : pass this assertion
    // Assert.assertEquals("#cdata-section",
    // ageProperty.getFirstChild().getNodeName());

    // "spouse" child bean assertions
    NamedNodeMap innerBeanAgePropertyAttributes = beanList.item(1).getChildNodes().item(
        0).getAttributes();
    Assert.assertEquals("age",
        innerBeanAgePropertyAttributes.getNamedItem("name").getNodeValue());
    Assert.assertEquals("11",
        innerBeanAgePropertyAttributes.getNamedItem("value").getNodeValue());

    // bean from "util" namespace
    Element name = (Element) testBean.getNextSibling();
    Assert.assertEquals("property-path", name.getTagName());
    Assert.assertEquals("property-path", name.getNodeName());
    Assert.assertEquals("http://www.springframework.org/schema/util",
        name.getNamespaceURI());

  }

  @Test
  public void checkParseSimple() {
    // Arrange
    String simpleXML = "<tags><tag>value</tag></tags>";

    // Act
    Document doc = XMLParser.parse(simpleXML);

    // Assert
    NodeList tags = doc.getElementsByTagName("tag");
    Assert.assertEquals("<tag>value</tag>", tags.item(0).toString());
    Text text = (Text) tags.item(0).getChildNodes().item(0);
    Assert.assertEquals("value", text.getData());
    Assert.assertEquals("#text", tags.item(0).getFirstChild().getNodeName());
    Assert.assertEquals("<tags><tag>value</tag></tags>",
        doc.getDocumentElement().toString());
  }

  @Test
  public void checkRemoveWhitespace() throws Exception {
    // Arrange
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

    // Act
    XMLParser.removeWhitespace(document);

    // Assert
    Assert.assertEquals(0, child.getChildNodes().getLength());
    // empty cdata is not removed
    Assert.assertEquals(1, child2.getChildNodes().getLength());
  }

  private String convertXMLFileToString(String fileName) {
    try {
      InputStream inputStream = this.getClass().getResourceAsStream(fileName);
      org.w3c.dom.Document doc = XmlUtils.newDocumentBuilder().parse(
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
