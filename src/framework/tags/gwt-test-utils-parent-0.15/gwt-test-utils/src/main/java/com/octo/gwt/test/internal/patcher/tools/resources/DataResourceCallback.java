package com.octo.gwt.test.internal.patcher.tools.resources;

import java.io.File;
import java.lang.reflect.Method;

import com.google.gwt.resources.client.ClientBundle;

public class DataResourceCallback extends AbstractClientBundleCallback {

	private String url;

	protected DataResourceCallback(Class<? extends ClientBundle> wrappedClass, File resourceFile, Class<? extends ClientBundle> proxiedClass) {
		super(wrappedClass, resourceFile);
		url = computeUrl(resourceFile, proxiedClass);
	}

	public Object call(Object proxy, Method method, Object[] args) throws Exception {
		if (method.getName().equals("getUrl")) {
			return url;
		}

		return null;

	}

}
