package com.octo.gwt.test.internal.patcher.tools.resources;

import java.io.File;
import java.lang.reflect.Method;

import com.google.gwt.resources.client.ClientBundle;

public class ImageResourceCallback extends AbstractClientBundleCallback {

	private String url;

	protected ImageResourceCallback(Class<? extends ClientBundle> wrappedClass, File resourceFile, Class<? extends ClientBundle> proxiedClass) {
		super(wrappedClass, resourceFile);
		url = computeUrl(resourceFile, proxiedClass);
	}

	public Object call(Object proxy, Method method, Object[] args) throws Exception {
		if (method.getName().equals("getURL")) {
			return url;
		} else if (method.getName().equals("getHeight")) {
			return 0;
		} else if (method.getName().equals("getLeft")) {
			return 0;
		} else if (method.getName().equals("getWidth")) {
			return 0;
		}

		return null;

	}

}
