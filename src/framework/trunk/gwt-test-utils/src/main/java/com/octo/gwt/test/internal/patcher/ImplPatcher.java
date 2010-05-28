package com.octo.gwt.test.internal.patcher;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class ImplPatcher extends AutomaticPatcher {

	public static String currentTestedModuleFile;

	@PatchMethod
	public static int getHashCode(Object o) {
		return o.hashCode();
	}
	
	@PatchMethod
	public static String getHostPageBaseURL() {
		return "http://localhost:8888/";
	}

	@PatchMethod
	public static String getModuleName() {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(getModuleConfigurationFile());

			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression = "/module/@rename-to";
			return xpath.evaluate(expression, document);
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new RuntimeException(e);
			}
		}
	}

	private static InputStream getModuleConfigurationFile() {
		String fileName = currentTestedModuleFile;
		if (fileName == null) {
			throw new IllegalArgumentException("GWT module configuration file (.gwt.xml) to be used is not set. You should ovveride "
					+ AbstractGwtTest.class.getSimpleName() + ".getModuleConfigurationFile() method");
		}
		return ImplPatcher.class.getClassLoader().getResourceAsStream(fileName);
	}

	@PatchMethod
	public static String getModuleBaseURL() {
		return getHostPageBaseURL() + getModuleName() + "/";
	}
	
	public static void reset() {
		currentTestedModuleFile = null;
	}

}
