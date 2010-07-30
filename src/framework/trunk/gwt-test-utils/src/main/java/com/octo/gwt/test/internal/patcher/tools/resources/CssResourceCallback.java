package com.octo.gwt.test.internal.patcher.tools.resources;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.octo.gwt.test.internal.patcher.tools.resources.CssResourceReader.CssParsingResult;

public class CssResourceCallback extends AbstractClientBundleCallback {

	private boolean alreadyInjected = false;

	protected CssResourceCallback(Class<? extends ClientBundle> wrappedClass, File resourceFile) {
		super(wrappedClass, resourceFile);
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

	private String getCssText() throws UnsupportedEncodingException, IOException {
		return TextResourceReader.readFile(resourceFile);
	}

	private boolean ensureInjected() throws UnsupportedEncodingException, IOException {
		if (!alreadyInjected) {
			StyleInjector.inject(getCssText());
			alreadyInjected = true;
			return true;
		}
		return false;
	}

	private String handleCustomMethod(String methodName) throws UnsupportedEncodingException, IOException {
		CssParsingResult result = CssResourceReader.readFile(resourceFile);
		String constant = result.getConstants().get(methodName);
		if (constant != null) {
			return constant;
		} else {
			return methodName;
		}
	}

}
