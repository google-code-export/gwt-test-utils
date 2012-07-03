package com.googlecode.gwt.test.internal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.cfg.ModuleDef;
import com.google.gwt.dev.cfg.ModuleDefLoader;
import com.google.gwt.dev.javac.JsniMethod;
import com.google.gwt.dev.javac.rebind.RebindCache;
import com.google.gwt.dev.shell.DispatchIdOracle;
import com.google.gwt.dev.shell.JsValue;
import com.google.gwt.dev.shell.ModuleSpace;
import com.google.gwt.dev.shell.ModuleSpaceHost;
import com.google.gwt.dev.shell.ShellModuleSpaceHost;
import com.googlecode.gwt.test.exceptions.GwtTestConfigurationException;
import com.googlecode.gwt.test.exceptions.GwtTestException;
import com.googlecode.gwt.test.internal.utils.XmlUtils;

/**
 * Class which provide all necessary information about a GWT module. <strong>For
 * internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class ModuleData {

  public static class ReplaceWithData {

    private final Map<String, List<String>> anyWhenPropertyIs = new HashMap<String, List<String>>();
    private final String replaceWith;
    private final Map<String, String> whenPropertyIs = new HashMap<String, String>();
    private final String whenTypeIs;

    public ReplaceWithData(String replaceWith, String whenTypeIs) {
      this.replaceWith = replaceWith;
      this.whenTypeIs = whenTypeIs;
    }

    public boolean anyMatch(String propName, String propValue) {
      List<String> any = anyWhenPropertyIs.get(propName);
      if (any == null) {
        return false;
      }

      for (String value : any) {
        if (propValue.equals(value)) {
          return true;
        }
      }
      return false;
    }

    public String getReplaceWith() {
      return replaceWith;
    }

    public String getWhenTypeIs() {
      return whenTypeIs;
    }

    public boolean hasAnyWhenPropertyIs() {
      return anyWhenPropertyIs.size() > 0;
    }

    public boolean hasWhenPropertyIs() {
      return whenPropertyIs.size() > 0;
    }

    public boolean whenPropertyIsMatch(String propName, String propValue) {
      if (propValue == null) {
        return false;
      }

      return propValue.equals(whenPropertyIs.get(propName));
    }

    void addAny(String propName, String propValue) {
      List<String> any = anyWhenPropertyIs.get(propName);
      if (any == null) {
        any = new ArrayList<String>();
        anyWhenPropertyIs.put(propName, any);
      }
      any.add(propValue);
    }

    void addWhenPropertyIs(String propName, String propValue) {
      whenPropertyIs.put(propName, propValue);
    }
  }

  private static final Map<String, ModuleData> CACHE = new HashMap<String, ModuleData>();

  private static final String[] CLASSPATH_ROOTS = new String[]{
      "src/main/java/", "src/main/resources/", "src/test/java/",
      "src/test/resources/", "src/", "resources/", "res/"};

  private static final RebindCache REBIND_CACHE = new RebindCache();

  public static ModuleData get(String moduleName) {
    ModuleData moduleData = CACHE.get(moduleName);
    if (moduleData == null) {
      moduleData = new ModuleData(moduleName);
      CACHE.put(moduleName, moduleData);
    }

    return moduleData;
  }

  private final Set<String> customGeneratedClasses;
  private ModuleDef moduleDef;
  private final String moduleName;
  private ModuleSpaceHost moduleSpaceHost;
  private final Set<String> parsedModules;

  private final Map<String, List<ReplaceWithData>> replaceWithListMap;

  private ModuleData(String moduleName) {
    this.moduleName = moduleName;
    this.replaceWithListMap = new HashMap<String, List<ReplaceWithData>>();
    this.customGeneratedClasses = new HashSet<String>();
    this.parsedModules = new HashSet<String>();

    parseModule(moduleName);
  }

  public Set<String> getCustomGeneratedClasses() {
    return customGeneratedClasses;
  }

  public ModuleDef getModuleDef() {
    if (moduleDef == null) {
      try {
        moduleDef = ModuleDefLoader.loadFromClassPath(
            TreeLoggerHolder.getTreeLogger(), moduleName, false);
      } catch (UnableToCompleteException e) {
        throw new GwtTestConfigurationException(
            "Error while creating ModuleDef for module '" + moduleName + "' :",
            e);
      }
    }

    return moduleDef;
  }

  public ModuleSpaceHost getModuleSpaceHost() {
    if (moduleSpaceHost == null) {
      try {
        moduleSpaceHost = new ShellModuleSpaceHost(
            TreeLoggerHolder.getTreeLogger(),
            getModuleDef().getCompilationState(TreeLoggerHolder.getTreeLogger()),
            getModuleDef(), null, null, REBIND_CACHE);
        ModuleSpace moduleSpace = createModuleSpace(moduleSpaceHost);
        moduleSpaceHost.onModuleReady(moduleSpace);
      } catch (UnableToCompleteException e) {
        throw new GwtTestConfigurationException(
            "Error while creating ModuleDef for module '" + moduleName + "' :",
            e);
      }
    }

    return moduleSpaceHost;
  }

  public Class<?> getRemoteServiceImplClass(String remoteServicePath) {

    if (!remoteServicePath.startsWith("/")) {
      remoteServicePath = "/" + remoteServicePath;
    }

    String servletClassName = getModuleDef().findServletForPath(
        remoteServicePath);

    if (servletClassName == null) {
      return null;
    }

    try {
      return Class.forName(servletClassName, true, GwtClassLoader.get());
    } catch (ClassNotFoundException e) {
      throw new GwtTestConfigurationException("Cannot find servlet class '"
          + servletClassName + "' configured for servlet path '"
          + remoteServicePath + "'");
    }
  }

  public Map<String, List<ReplaceWithData>> getReplaceWithListMap() {
    return replaceWithListMap;
  }

  private Document createDocument(String moduleFilePath) throws Exception {

    InputStream is = getModuleFileAsStream(moduleFilePath);

    try {
      DocumentBuilder builder = XmlUtils.newDocumentBuilder();
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

  private ModuleSpace createModuleSpace(ModuleSpaceHost host) {

    return new ModuleSpace(TreeLoggerHolder.getTreeLogger(), host,
        getModuleDef().getCanonicalName()) {

      public void createNativeMethods(TreeLogger logger,
          List<JsniMethod> jsniMethods, DispatchIdOracle dispatchIdOracle) {
        // this method should never be called
        throw new UnsupportedOperationException(
            "ModuleSpace.createNativeMethods(..) not supported by gwt-test-utils");
      }

      @Override
      protected void createStaticDispatcher(TreeLogger logger) {
        // this method should never be called
        throw new UnsupportedOperationException(
            "ModuleSpace.createStaticDispatcher(..) not supported by gwt-test-utils");

      }

      @Override
      protected JsValue doInvoke(String name, Object jthis, Class<?>[] types,
          Object[] args) throws Throwable {
        // this method should never be called
        throw new UnsupportedOperationException(
            "ModuleSpace.doInvoke(..) not supported by gwt-test-utils");
      }

      @Override
      protected Object getStaticDispatcher() {
        // this method should never be called
        throw new UnsupportedOperationException(
            "ModuleSpace.getStaticDispatcher() not supported by gwt-test-utils");

      }
    };

  }

  private InputStream getModuleFileAsStream(String moduleFilePath) {
    InputStream is = this.getClass().getClassLoader().getResourceAsStream(
        moduleFilePath);

    if (is != null) {
      return is;
    }

    for (String classpathRoot : CLASSPATH_ROOTS) {
      try {
        return new FileInputStream(classpathRoot + moduleFilePath);
      } catch (FileNotFoundException e) {
        // try next
      }
    }

    throw new GwtTestConfigurationException(
        "Cannot find GWT module configuration file '" + moduleFilePath
            + "' in the classpath");
  }

  private void initializeGenerateWith(Document document, XPath xpath)
      throws XPathExpressionException {

    NodeList whenTypeAssignableList = (NodeList) xpath.evaluate(
        "/module/generate-with/when-type-assignable[@class]", document,
        XPathConstants.NODESET);

    for (int i = 0; i < whenTypeAssignableList.getLength(); i++) {
      Node whenTypeAssignableWith = whenTypeAssignableList.item(i);

      String className = xpath.evaluate("@class", whenTypeAssignableWith);
      customGeneratedClasses.add(className);
    }
  }

  private void initializeInherits(Document document, XPath xpath)
      throws Exception {
    NodeList inherits = (NodeList) xpath.evaluate("/module/inherits", document,
        XPathConstants.NODESET);

    for (int i = 0; i < inherits.getLength(); i++) {
      Node inherit = inherits.item(i);
      String inheritName = xpath.evaluate("@name", inherit).trim();

      if (parsedModules.contains(inheritName)
          || inheritName.startsWith("com.google.gwt")) {
        continue;
      }

      parseModuleFile(inheritName, xpath);
    }
  }

  private void initializeReplaceWith(Document document, XPath xpath)
      throws XPathExpressionException {
    NodeList replaceWithList = (NodeList) xpath.evaluate(
        "/module/replace-with[@class]", document, XPathConstants.NODESET);

    for (int i = 0; i < replaceWithList.getLength(); i++) {
      Node replaceWith = replaceWithList.item(i);

      String replaceClass = xpath.evaluate("@class", replaceWith);
      String whenTypeIsClass = xpath.evaluate("when-type-is/@class",
          replaceWith);

      ReplaceWithData data = new ReplaceWithData(replaceClass, whenTypeIsClass);

      List<ReplaceWithData> list = replaceWithListMap.get(data.whenTypeIs);
      if (list == null) {
        list = new ArrayList<ReplaceWithData>();
        replaceWithListMap.put(data.whenTypeIs, list);
      }

      // Handle when-property-is list
      NodeList whenPropertyIsList = (NodeList) xpath.evaluate(
          "when-property-is", replaceWith, XPathConstants.NODESET);

      for (int j = 0; j < whenPropertyIsList.getLength(); j++) {
        Node whenPropertyIs = whenPropertyIsList.item(j);
        String name = xpath.evaluate("@name", whenPropertyIs);
        String value = xpath.evaluate("@value", whenPropertyIs);
        data.addWhenPropertyIs(name, value);
      }

      // Handle any/when-property-is list
      NodeList anyWhenPropertyIsList = (NodeList) xpath.evaluate(
          "any/when-property-is", replaceWith, XPathConstants.NODESET);

      for (int j = 0; j < anyWhenPropertyIsList.getLength(); j++) {
        Node anyWhenPropertyIs = anyWhenPropertyIsList.item(j);
        String name = xpath.evaluate("@name", anyWhenPropertyIs);
        String value = xpath.evaluate("@value", anyWhenPropertyIs);

        data.addAny(name, value);
      }

      list.add(data);
    }
  }

  private void parseModule(String moduleName) {

    try {
      XPath xpath = XPathFactory.newInstance().newXPath();

      parseModuleFile(moduleName, xpath);

    } catch (Exception e) {
      if (GwtTestException.class.isInstance(e)) {
        throw (GwtTestException) e;
      } else {
        throw new GwtTestConfigurationException(
            "Error while parsing GWT module '" + moduleName + "'", e);
      }
    }
  }

  /**
   * parse .gwt.xml file to get fill {@link ModuleData} information
   * 
   * @param moduleName The module name
   * @param xpath
   * @throws Exception
   */
  private void parseModuleFile(String moduleName, XPath xpath) throws Exception {

    parsedModules.add(moduleName);

    String moduleFilePath = moduleName.replaceAll("\\.", "/") + ".gwt.xml";
    Document document = createDocument(moduleFilePath);

    initializeInherits(document, xpath);
    initializeReplaceWith(document, xpath);
    initializeGenerateWith(document, xpath);
  }

}
