package com.octo.gwt.test.internal.patcher.tools.resources;

import java.io.File;
import java.lang.reflect.Method;

import com.google.gwt.resources.client.ClientBundle;
import com.octo.gwt.test.internal.patcher.tools.TextResourceReader;

public class CssResourceCallback extends AbstractClientBundleCallback {
	
	protected CssResourceCallback(Class<? extends ClientBundle> wrappedClass,  File resourceFile) {
		super(wrappedClass, resourceFile);
	}

	public Object call(Object proxy, Method method, Object[] args) throws Exception {
		if (method.getName().equals("getText")) {
			return TextResourceReader.readFile(resourceFile);
		} 
		
		return null;
		
	}

}
