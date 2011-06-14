package com.octo.gwt.test.internal.patchers.dom;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestDomException;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.internal.utils.DoubleMap;
import com.octo.gwt.test.internal.utils.GwtHtmlParser;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(Document.class)
public class DocumentPatcher {

  private static Document DOCUMENT;

  private static final DoubleMap<String, String, Element> HTML_ELEMENT_PROTOTYPES = new DoubleMap<String, String, Element>();

  private static int ID = 0;

  public static void reset() {
    if (DOCUMENT != null) {
      if (DOCUMENT.getBody() != null) {
        JavaScriptObjects.clearProperties(DOCUMENT.getBody());
      }
      JavaScriptObjects.clearProperties(DOCUMENT);
      DOCUMENT = null;
      GwtReflectionUtils.setStaticField(Document.class, "doc", null);
    }
  }

  @PatchMethod
  static Text createTextNode(Document document, String data) {
    return JavaScriptObjects.newText(data);
  }

  @PatchMethod
  static String createUniqueId(Document document) {
    ID++;
    return "elem_" + Long.toString(ID);
  }

  @PatchMethod
  static BodyElement getBody(Document document) {
    NodeList<Element> bodyList = getElementsByTagName(document, "body");
    if (bodyList.getLength() < 1)
      return null;
    else
      return bodyList.getItem(0).cast();
  }

  @PatchMethod
  static String getCompatMode(Document document) {
    return "toto";
  }

  @PatchMethod
  static String getDomain(Document document) {
    return null;
  }

  @PatchMethod
  static Element getElementById(Node document, String elementId) {
    NodeList<Node> childs = getChildNodeList(document);

    for (int i = 0; i < childs.getLength(); i++) {
      Node n = childs.getItem(i);
      if (Node.ELEMENT_NODE == n.getNodeType()) {
        Element currentElement = n.cast();
        if (elementId.equals(currentElement.getId())) {
          return currentElement;
        }
      }
      Element result = getElementById(n, elementId);
      if (result != null) {
        return result;
      }
    }

    return null;
  }

  @PatchMethod
  static NodeList<Element> getElementsByTagName(Node node, String tagName) {
    NodeList<Element> result = JavaScriptObjects.newNodeList();

    inspectDomForTag(node, tagName, result);

    return result;
  }

  @PatchMethod
  static String getReferrer(Document document) {
    return "";
  }

  @PatchMethod
  static Document nativeGet() {
    if (DOCUMENT == null) {
      try {
        DOCUMENT = JavaScriptObjects.newObject(Document.class);
        Element e = parseHTMLElement();
        DOCUMENT.appendChild(e);
        JavaScriptObjects.setProperty(DOCUMENT, JsoProperties.DOCUMENT_ELEMENT,
            e);

        return DOCUMENT;
      } catch (Exception e) {
        if (GwtTestException.class.isInstance(e)) {
          throw (GwtTestException) e;
        } else {
          throw new GwtTestDomException("Unable to create Document", e);
        }
      }
    }
    return DOCUMENT;
  }

  private static Element findHTMLElement(String hostPagePath,
      NodeList<Node> nodes) {
    int i = 0;
    while (i < nodes.getLength()) {
      Node node = nodes.getItem(i);
      if (Node.ELEMENT_NODE == node.getNodeType()) {
        Element e = node.cast();
        if ("html".equalsIgnoreCase(e.getTagName())) {
          return e;
        }
      }
      i++;
    }
    throw new GwtTestDomException("Cannot find a root <html> element in file '"
        + hostPagePath + "'");
  }

  private static NodeList<Node> getChildNodeList(Node node) {
    return JavaScriptObjects.getObject(node, JsoProperties.NODE_LIST_FIELD);
  }

  private static String getHostPageHTML(String hostPagePath) {

    InputStream is = JavaScriptObjects.class.getClassLoader().getResourceAsStream(
        hostPagePath);

    if (is == null) {
      try {
        is = new FileInputStream(hostPagePath);
      } catch (FileNotFoundException e) {
        // handle just after
      }
    }
    if (is == null) {
      throw new GwtTestConfigurationException(
          "Cannot find file '"
              + hostPagePath
              + "', please override "
              + GwtTest.class.getSimpleName()
              + ".getHostPagePath() method by specifying the relative path from the root directory of your java project");
    }
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }

      return sb.toString();
    } catch (IOException e) {
      throw new GwtTestConfigurationException(
          "Error while reading module HTML host page '" + hostPagePath + "'", e);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          // don't care
        }
      }
    }

  }

  private static void inspectDomForTag(Node node, String tagName,
      NodeList<Element> result) {
    NodeList<Node> childs = getChildNodeList(node);
    List<Node> list = JavaScriptObjects.getObject(result,
        JsoProperties.NODE_LIST_INNER_LIST);

    for (int i = 0; i < childs.getLength(); i++) {
      Node n = childs.getItem(i);
      if (Node.ELEMENT_NODE == n.getNodeType()) {
        Element childElem = n.cast();
        if ("*".equals(tagName)
            || tagName.equalsIgnoreCase(childElem.getTagName())) {
          list.add(childElem);
        }
      }
      inspectDomForTag(n, tagName, result);
    }
  }

  private static Element parseHTMLElement() {
    String moduleName = GwtConfig.get().getModuleName();
    String hostPagePath = GwtConfig.get().getHostPagePath();

    if (hostPagePath == null) {
      throw new GwtTestConfigurationException(
          "Cannot find the actual HTML host page for module '"
              + GWT.getModuleName()
              + "'. You should override "
              + GwtTest.class.getName()
              + ".getHostPagePath(String moduleFullQualifiedName) method to specify it.");
    }

    Element htmlPrototype = HTML_ELEMENT_PROTOTYPES.get(moduleName,
        hostPagePath);

    if (htmlPrototype == null) {
      // parsing of the host page
      String html = getHostPageHTML(hostPagePath);
      NodeList<Node> list = GwtHtmlParser.parse(html);

      htmlPrototype = findHTMLElement(hostPagePath, list);
      HTML_ELEMENT_PROTOTYPES.put(moduleName, hostPagePath, htmlPrototype);
    }

    return htmlPrototype.cloneNode(true).cast();
  }

}
