package com.octo.gwt.test.internal.patcher.tools.resources;

import java.lang.reflect.Method;
import java.net.URL;

import com.google.gwt.resources.client.ClientBundle;

public class TextResourceCallback extends AbstractClientBundleCallback {

	protected TextResourceCallback(Class<? extends ClientBundle> wrappedClass, URL resourceURL) {
		super(wrappedClass, resourceURL);
	}

	public Object call(Object proxy, Method method, Object[] args) throws Exception {
		if (method.getName().equals("getText")) {
			return TextResourceReader.readFile(resourceURL);
		}

		return null;

	}

}
