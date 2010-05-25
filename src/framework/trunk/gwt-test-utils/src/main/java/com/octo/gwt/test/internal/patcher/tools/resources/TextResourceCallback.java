package com.octo.gwt.test.internal.patcher.tools.resources;

import java.io.File;
import java.lang.reflect.Method;

import com.google.gwt.resources.client.ClientBundle;

public class TextResourceCallback extends AbstractClientBundleCallback {
	
	protected TextResourceCallback(Class<? extends ClientBundle> wrappedClass,  File resourceFile) {
		super(wrappedClass, resourceFile);
	}

	public Object call(Object proxy, Method method, Object[] args) throws Exception {
		if (method.getName().equals("getText")) {
			return TextResourceReader.readFile(resourceFile);
		} 
		
		return null;
		
	}




}
