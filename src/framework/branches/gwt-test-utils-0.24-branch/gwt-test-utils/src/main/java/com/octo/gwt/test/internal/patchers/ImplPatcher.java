package com.octo.gwt.test.internal.patchers;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.w3c.dom.Document;

import com.google.gwt.core.client.impl.Impl;
import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Impl.class)
public class ImplPatcher {

	public static String currentTestedModuleFile;

	private static String currentModuleName;

	@PatchMethod
	public static int getHashCode(Object o) {
		return HashCodeBuilder.reflectionHashCode(o);
	}

	@PatchMethod
	public static String getHostPageBaseURL() {
		return "http://127.0.0.1:8888/";
	}

	@PatchMethod
	public static String getModuleName() {
		if (currentModuleName == null) {
			try {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = builder.parse(getModuleConfigurationFile());

				XPath xpath = XPathFactory.newInstance().newXPath();
				String expression = "/module/@rename-to";
				currentModuleName = xpath.evaluate(expression, document);
			} catch (Exception e) {
				if (e instanceof RuntimeException) {
					throw (RuntimeException) e;
				} else {
					throw new RuntimeException(e);
				}
			}
		}
		return currentModuleName;
	}

	private static InputStream getModuleConfigurationFile() {
		String fileName = currentTestedModuleFile;
		if (fileName == null) {
			throw new IllegalArgumentException("GWT module configuration file (.gwt.xml) to be used is not set. You should override "
					+ GwtTest.class.getSimpleName() + ".getCurrentTestedModuleFile() method in your test class");
		}
		return ImplPatcher.class.getClassLoader().getResourceAsStream(fileName);
	}

	@PatchMethod
	public static String getModuleBaseURL() {
		return getHostPageBaseURL() + getModuleName() + "/";
	}

	public static void reset() {
		currentTestedModuleFile = null;
		currentModuleName = null;
	}

}
