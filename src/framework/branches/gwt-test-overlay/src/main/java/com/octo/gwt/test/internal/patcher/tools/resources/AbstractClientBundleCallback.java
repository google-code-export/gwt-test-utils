package com.octo.gwt.test.internal.patcher.tools.resources;

import java.io.File;

import com.google.gwt.resources.client.ClientBundle;

public abstract class AbstractClientBundleCallback implements IClientBundleCallback {
	
	protected File resourceFile;
	protected Class<? extends ClientBundle> wrappedClass;
	
	protected AbstractClientBundleCallback(Class<? extends ClientBundle> wrappedClass, File resourceFile) {
		this.wrappedClass = wrappedClass;
		this.resourceFile = resourceFile;
	}

	public Class<? extends ClientBundle> getWrappedClass() {
		return wrappedClass;
	}

}
