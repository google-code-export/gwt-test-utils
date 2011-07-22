package com.octo.gwt.test.internal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.internal.utils.XmlUtils;

/**
 * Class which provide all necessary information about a GWT module. <strong>For
 * internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class ModuleData {

	private static final String[] CLASSPATH_ROOTS = new String[] { "src/main/java/", "src/main/resources/", "src/test/java/", "src/test/resources/",
			"src/", "resources/", "res/" };

	private static final Set<String> DEFAULT_CLIENT_PATHS = new HashSet<String>();

	static {
		DEFAULT_CLIENT_PATHS.add("com.google.gwt.");
		DEFAULT_CLIENT_PATHS.add("com.octo.gwt.");
		DEFAULT_CLIENT_PATHS.add("com.extjs.gxt.");
	}

	private static final ModuleData INSTANCE = new ModuleData();

	public static ModuleData get() {
		return INSTANCE;
	}

	private final Set<String> clientExclusions;
	private final Set<String> clientPaths;
	private final Map<String, String> moduleAlias;

	private final Map<String, String> remoteServiceImpls;

	private ModuleData() {
		moduleAlias = new HashMap<String, String>();

		clientPaths = new HashSet<String>();
		clientPaths.addAll(DEFAULT_CLIENT_PATHS);

		clientExclusions = new HashSet<String>();

		remoteServiceImpls = new HashMap<String, String>();
	}

	public Set<String> getClientExclusions() {
		return clientExclusions;
	}

	public Set<String> getClientPaths() {
		return clientPaths;
	}

	public String getModuleAlias(String module) {
		return moduleAlias.get(module);
	}

	public Set<String> getModuleNames() {
		return Collections.unmodifiableSet(moduleAlias.keySet());
	}

	public Class<?> getRemoteServiceImplClass(String remoteServicePath) {
		if (!remoteServicePath.startsWith("/")) {
			remoteServicePath = "/" + remoteServicePath;
		}

		String servletClassName = remoteServiceImpls.get(remoteServicePath);

		if (servletClassName == null) {
			return null;
		}

		try {
			return Class.forName(servletClassName, true, GwtClassLoader.get());
		} catch (ClassNotFoundException e) {
			throw new GwtTestConfigurationException("Cannot find servlet class '" + servletClassName + "' configured for servlet path '"
					+ remoteServicePath + "'");
		}
	}

	public void parseModule(String moduleFilePath) {
		try {
			Document document = createDocument(moduleFilePath);
			XPath xpath = XPathFactory.newInstance().newXPath();

			// parse .gwt.xml file to get the client package in the module and in the
			// inherited modules
			parseModuleFile(moduleFilePath, document, xpath);

		} catch (Exception e) {
			if (GwtTestException.class.isInstance(e)) {
				throw (GwtTestException) e;
			} else {
				throw new GwtTestConfigurationException(e);
			}
		}
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

	private String getModuleAlias(Document document, XPath xpath, String moduleFilePath) throws XPathExpressionException {
		return xpath.evaluate("/module/@rename-to", document).trim();
	}

	private InputStream getModuleFileAsStream(String moduleFilePath) {
		InputStream is = GwtConfig.class.getClassLoader().getResourceAsStream(moduleFilePath);

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

		throw new GwtTestConfigurationException("Cannot find GWT module configuration file '" + moduleFilePath + "' in the classpath");
	}

	private String getModuleName(String moduleFilePath) {
		return moduleFilePath.substring(0, moduleFilePath.toLowerCase().indexOf(".gwt.xml")).replaceAll("/", ".");
	}

	private String getModulePackage(String moduleFilePath) {
		return moduleFilePath.substring(0, moduleFilePath.lastIndexOf("/") + 1).replaceAll("/", ".");
	}

	private void initializeClientPaths(String modulePackage, Document document, XPath xpath) throws XPathExpressionException {
		NodeList sources = (NodeList) xpath.evaluate("/module/source", document, XPathConstants.NODESET);

		for (int i = 0; i < sources.getLength(); i++) {
			Node source = sources.item(i);
			String sourcePath = xpath.evaluate("@path", source).trim().replaceAll("/", ".");

			if (sourcePath.length() < 1)
				continue;

			sourcePath = sourcePath.endsWith(".") ? modulePackage + sourcePath : modulePackage + sourcePath + ".";
			clientPaths.add(sourcePath);

			initializeExclusionPaths(xpath, source, sourcePath);

		}
	}

	private void initializeExclusionPaths(XPath xpath, Node source, String sourcePath) throws XPathExpressionException {
		NodeList exclusionNodes = (NodeList) xpath.evaluate("exclude", source, XPathConstants.NODESET);

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

	private void initializeInherits(Document document, XPath xpath) throws Exception {
		NodeList inherits = (NodeList) xpath.evaluate("/module/inherits", document, XPathConstants.NODESET);

		for (int i = 0; i < inherits.getLength(); i++) {
			Node inherit = inherits.item(i);
			String inheritName = xpath.evaluate("@name", inherit).trim();

			if (moduleAlias.containsKey(inheritName) || moduleAlias.containsValue(inheritName) || isDefaultClientPathModule(inheritName)) {
				continue;
			}

			String inheritModuleFilePath = inheritName.replaceAll("\\.", "/") + ".gwt.xml";

			Document inheritModuleDocument = createDocument(inheritModuleFilePath);
			parseModuleFile(inheritModuleFilePath, inheritModuleDocument, xpath);
		}
	}

	private boolean isDefaultClientPathModule(String moduleName) {
		for (String defaultClientPath : DEFAULT_CLIENT_PATHS) {
			if (moduleName.startsWith(defaultClientPath)) {
				return true;
			}
		}

		return false;
	}

	private void initializeServlets(String moduleFilePath, Document document, XPath xpath) throws Exception {
		NodeList servlets = (NodeList) xpath.evaluate("/module/servlet", document, XPathConstants.NODESET);

		for (int i = 0; i < servlets.getLength(); i++) {
			Node servlet = servlets.item(i);
			String servletPath = xpath.evaluate("@path", servlet).trim();

			if (servletPath == null || "".equals(servletPath.trim())) {
				throw new GwtTestConfigurationException("Error in file '" + moduleFilePath + "' : <servlet> declared without a correct 'path' value");
			}

			String servletClassName = xpath.evaluate("@class", servlet).trim();

			if (servletClassName == null || "".equals(servletClassName.trim())) {
				throw new GwtTestConfigurationException("Error in file '" + moduleFilePath + "' : <servlet> declared without a correct 'class' value");
			}

			if (!servletPath.startsWith("/")) {
				servletPath = "/" + servletPath;
			}

			remoteServiceImpls.put(servletPath, servletClassName);

		}
	}

	private void parseModuleFile(String moduleFilePath, Document document, XPath xpath) throws Exception {

		String moduleName = getModuleName(moduleFilePath);
		String alias = getModuleAlias(document, xpath, moduleFilePath);
		if (alias == null || "".equals(alias.trim())) {
			alias = moduleName;
		}

		moduleAlias.put(moduleName, alias);

		String modulePackage = getModulePackage(moduleFilePath);
		initializeClientPaths(modulePackage, document, xpath);
		initializeInherits(document, xpath);
		initializeServlets(moduleFilePath, document, xpath);
	}

}
