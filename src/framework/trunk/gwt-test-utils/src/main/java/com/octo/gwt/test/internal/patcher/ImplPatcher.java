package com.octo.gwt.test.internal.patcher;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.octo.gwt.test.AbstractGWTTest;
import com.octo.gwt.test.PatchGWT;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class ImplPatcher extends AutomaticPatcher {

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
		String fileName = PatchGWT.getCurrentTestedModuleFile();
		if (fileName == null) {
			throw new IllegalArgumentException("GWT module configuration file (.gwt.xml) to be used is not set. You should ovveride "
					+ AbstractGWTTest.class.getSimpleName() + ".getModuleConfigurationFile() method");
		}
		return ImplPatcher.class.getClassLoader().getResourceAsStream(fileName);
	}

	@PatchMethod
	public static String getModuleBaseURL() {
		return getHostPageBaseURL() + getModuleName() + "/";
	}

}