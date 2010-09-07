package com.octo.gwt.test.internal.patcher.tools.resources;

import java.lang.reflect.Method;
import java.net.URL;

import com.google.gwt.resources.client.ClientBundle;

public class DataResourceCallback extends AbstractClientBundleCallback {

	private String url;

	protected DataResourceCallback(Class<? extends ClientBundle> wrappedClass, URL resourceURL, Class<? extends ClientBundle> proxiedClass) {
		super(wrappedClass, resourceURL);
		url = computeUrl(resourceURL, proxiedClass);
	}

	public Object call(Object proxy, Method method, Object[] args) throws Exception {
		if (method.getName().equals("getUrl")) {
			return url;
		}

		return null;

	}

}
