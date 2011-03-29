package com.octo.gwt.test.internal;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModuleData {

  private static ModuleData INSTANCE;

  public static ModuleData get() {
    if (INSTANCE == null) {
      INSTANCE = new ModuleData();
    }

    return INSTANCE;
  }

  private List<String> clientExclusions;
  private List<String> clientPaths;
  private List<String> moduleNames;

  private ModuleData() {
    moduleNames = new ArrayList<String>();

    clientPaths = new ArrayList<String>();
    clientPaths.add("com.google.gwt.");
    clientPaths.add("com.octo.gwt.");
    clientPaths.add("com.extjs.gxt.");

    clientExclusions = new ArrayList<String>();
  }

  public List<String> getClientExclusions() {
    return clientExclusions;
  }

  public List<String> getClientPaths() {
    return clientPaths;
  }

  public List<String> getModuleNames() {
    return moduleNames;
  }

  public void parseModule(String moduleFilePath) {
    try {
      Document document = createDocument(moduleFilePath);
      XPath xpath = XPathFactory.newInstance().newXPath();

      moduleNames.add(getModuleName(document, xpath, moduleFilePath));
      // parse .gwt.xml file to get the client package in the module and in the
      // inherited modules
      parseModuleFile(moduleFilePath, document, xpath);

    } catch (Exception e) {
      if (e instanceof RuntimeException) {
        throw (RuntimeException) e;
      } else {
        throw new RuntimeException(e);
      }
    }
  }

  private Document createDocument(String moduleFilePath) throws Exception {

    InputStream is = GwtConfig.class.getClassLoader().getResourceAsStream(
        moduleFilePath);

    if (is == null) {
      throw new IllegalArgumentException(
          "Cannot find GWT module configuration file '" + moduleFilePath
              + "', please see the " + ClassLoader.class.getSimpleName()
              + ".getResourceAsStream(String path) method for more information");
    }

    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      return builder.parse(is);
    } finally {
      // close the stream
      try {
        is.close();
      } catch (Exception ioException) {
        // nothing to do
      }
    }
  }

  private String getModuleName(Document document, XPath xpath,
      String moduleFilePath) throws XPathExpressionException {
    String moduleName = xpath.evaluate("/module/@rename-to", document).trim();

    if (moduleName.length() < 1)
      moduleName = moduleFilePath.substring(0,
          moduleFilePath.toLowerCase().indexOf(".gwt.xml")).replaceAll("/", ".");

    return moduleName;
  }

  private String getModulePackage(String moduleFilePath) {
    return moduleFilePath.substring(0, moduleFilePath.lastIndexOf("/") + 1).replaceAll(
        "/", ".");
  }

  private void initializeClientPaths(String modulePackage, Document document,
      XPath xpath) throws XPathExpressionException {
    NodeList sources = (NodeList) xpath.evaluate("/module/source", document,
        XPathConstants.NODESET);

    for (int i = 0; i < sources.getLength(); i++) {
      Node source = sources.item(i);
      String sourcePath = xpath.evaluate("@path", source).trim().replaceAll(
          "/", ".");

      if (sourcePath.length() < 1)
        continue;

      sourcePath = sourcePath.endsWith(".") ? modulePackage + sourcePath
          : modulePackage + sourcePath + ".";
      clientPaths.add(sourcePath);

      initializeExclusionPaths(xpath, source, sourcePath);

    }
  }

  private void initializeExclusionPaths(XPath xpath, Node source,
      String sourcePath) throws XPathExpressionException {
    NodeList exclusionNodes = (NodeList) xpath.evaluate("exclude", source,
        XPathConstants.NODESET);

    for (int j = 0; j < exclusionNodes.getLength(); j++) {
      Node exclusion = exclusionNodes.item(j);
      String exclusionName = xpath.evaluate("@name", exclusion).trim();
      int extensionToken = (exclusionName.toLowerCase().indexOf(".java"));
      if (extensionToken > -1) {
        exclusionName = exclusionName.substring(0, extensionToken).trim();
      }
      if (exclusionName.length() > 0) {
        clientExclusions.add(sourcePath + exclusionName);
      }
    }
  }

  private void initializeInherits(Document document, XPath xpath)
      throws Exception {
    NodeList inherits = (NodeList) xpath.evaluate("/module/inherits", document,
        XPathConstants.NODESET);

    for (int i = 0; i < inherits.getLength(); i++) {
      Node inherit = inherits.item(i);
      String inheritName = xpath.evaluate("@name", inherit).trim();

      if (inheritName.length() < 1 || isAlreadyParsed(inheritName))
        continue;

      String inheritModuleFilePath = inheritName.replaceAll("\\.", "/")
          + ".gwt.xml";

      Document inheritModuleDocument = createDocument(inheritModuleFilePath);
      parseModuleFile(inheritModuleFilePath, inheritModuleDocument, xpath);
    }
  }

  private boolean isAlreadyParsed(String inheritName) {
    for (String clientPath : clientPaths) {
      if (inheritName.startsWith(clientPath)) {
        return true;
      }
    }

    return false;
  }

  private void parseModuleFile(String moduleFilePath, Document document,
      XPath xpath) throws Exception {

    String modulePackage = getModulePackage(moduleFilePath);
    initializeClientPaths(modulePackage, document, xpath);

    initializeInherits(document, xpath);
  }

}
