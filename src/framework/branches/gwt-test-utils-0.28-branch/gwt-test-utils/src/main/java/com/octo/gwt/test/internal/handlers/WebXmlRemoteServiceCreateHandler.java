package com.octo.gwt.test.internal.handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.integration.RemoteServiceCreateHandler;
import com.octo.gwt.test.internal.utils.XmlUtils;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class WebXmlRemoteServiceCreateHandler extends RemoteServiceCreateHandler {

  private static final String[] WAR_ROOTS = new String[]{
      "war/", "src/main/webapp/"};

  private static final String WEB_XML = "WEB-INF/web.xml";

  // a map with servletUrl as key and serviceImpl instance as value
  private final Map<String, Object> servicesImplMap = new HashMap<String, Object>();

  // a map with servletUrl as key and serviceImpl className as value
  private Map<String, String> servletClassMap;

  private InputStream getWebXmlAsStream() {
    for (String warRoot : WAR_ROOTS) {
      try {
        return new FileInputStream(warRoot + WEB_XML);
      } catch (FileNotFoundException e) {
        // try next
      }
    }

    throw new GwtTestConfigurationException(
        "Cannot find 'web.xml' file ' for GWT module " + GWT.getModuleName());
  }

  private Map<String, String> parseMappingElements(Document document,
      XPath xpath) throws XPathExpressionException {
    NodeList mappings = (NodeList) xpath.evaluate("/web-app/servlet-mapping",
        document, XPathConstants.NODESET);

    Map<String, String> mappingsMap = new HashMap<String, String>();

    for (int i = 0; i < mappings.getLength(); i++) {
      Node mapping = mappings.item(i);
      String servletName = xpath.evaluate("servlet-name", mapping);
      String urlPattern = xpath.evaluate("url-pattern", mapping);
      if (!urlPattern.startsWith("/")) {
        urlPattern = "/" + urlPattern;
      }
      mappingsMap.put(servletName, urlPattern);
    }
    return mappingsMap;
  }

  private Map<String, String> parseServletElements(Document document,
      XPath xpath) throws XPathExpressionException {
    NodeList servlets = (NodeList) xpath.evaluate("/web-app/servlet", document,
        XPathConstants.NODESET);

    Map<String, String> servletsMap = new HashMap<String, String>();

    for (int i = 0; i < servlets.getLength(); i++) {
      Node servlet = servlets.item(i);
      String servletName = xpath.evaluate("servlet-name", servlet);
      String servletClass = xpath.evaluate("servlet-class", servlet);
      servletsMap.put(servletName, servletClass);
    }
    return servletsMap;
  }

  /**
   * Find and parse 'web.xml' file to retrieve its servlets information.
   * 
   * @return A map with servlet-url as key and servlet-class as value
   */
  private Map<String, String> parseWebXmlFile() {
    InputStream is = getWebXmlAsStream();
    try {
      DocumentBuilder builder = XmlUtils.newDocumentBuilder();
      Document document = builder.parse(is);
      XPath xpath = XPathFactory.newInstance().newXPath();

      Map<String, String> servletsMap = parseServletElements(document, xpath);
      Map<String, String> mappingsMap = parseMappingElements(document, xpath);

      // create the final map with servlets and servlet-mappings information
      Map<String, String> result = new HashMap<String, String>();
      for (Map.Entry<String, String> mappingsEntry : mappingsMap.entrySet()) {
        result.put(mappingsEntry.getValue(),
            servletsMap.get(mappingsEntry.getKey()));
      }
      return result;

    } catch (Exception e) {
      if (GwtTestException.class.isInstance(e)) {
        throw (GwtTestException) e;
      } else {
        throw new GwtTestConfigurationException("Error while parsing web.xml",
            e);
      }
    } finally {
      // close the stream
      try {
        is.close();
      } catch (Exception ioException) {
        // nothing to do
      }
    }
  }

  @Override
  protected Object findService(Class<?> remoteServiceClass,
      String remoteServiceRelativePath) {

    String servletPath = "/" + GWT.getModuleName() + "/"
        + remoteServiceRelativePath;

    Object serviceImpl = servicesImplMap.get("servletPath");

    if (serviceImpl != null) {
      return serviceImpl;
    }

    if (servletClassMap == null) {
      servletClassMap = parseWebXmlFile();
    }

    String className = servletClassMap.get(servletPath);

    if (className == null) {
      return null;
    }

    try {
      serviceImpl = GwtReflectionUtils.instantiateClass(Class.forName(className));
    } catch (ClassNotFoundException e) {
      // should not happen..
      throw new GwtTestConfigurationException(e);
    }

    // cache the implementation
    servicesImplMap.put(servletPath, serviceImpl);

    return serviceImpl;
  }

}
