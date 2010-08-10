package com.octo.gwt.test.internal.patcher.tools.resources;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.octo.gwt.test.internal.patcher.tools.resources.CssResourceReader.CssParsingResult;

public class CssResourceCallback extends AbstractClientBundleCallback {

	private boolean alreadyInjected = false;

	protected CssResourceCallback(Class<? extends ClientBundle> wrappedClass, URL resourceURL) {
		super(wrappedClass, resourceURL);
	}

	public Object call(Object proxy, Method method, Object[] args) throws Exception {
		if (method.getName().equals("getText")) {
			return getCssText();
		} else if (method.getName().equals("ensureInjected")) {
			return ensureInjected();
		} else {
			return handleCustomMethod(method.getName());
		}

	}

	private String getCssText() throws UnsupportedEncodingException, IOException, URISyntaxException {
		return TextResourceReader.readFile(new File(resourceURL.toURI()));
	}

	private boolean ensureInjected() throws UnsupportedEncodingException, IOException, URISyntaxException {
		if (!alreadyInjected) {
			StyleInjector.inject(getCssText());
			alreadyInjected = true;
			return true;
		}
		return false;
	}

	private String handleCustomMethod(String methodName) throws UnsupportedEncodingException, IOException, URISyntaxException {
		CssParsingResult result = CssResourceReader.readFile(new File(resourceURL.toURI()));
		String constant = result.getConstants().get(methodName);
		if (constant != null) {
			return constant;
		} else {
			return methodName;
		}
	}

}
