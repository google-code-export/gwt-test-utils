package com.octo.gwt.test.internal.patcher.tools.resources;

import java.net.URL;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public abstract class AbstractClientBundleCallback implements IClientBundleCallback {

	protected URL resourceURL;
	protected Class<? extends ClientBundle> wrappedClass;

	protected AbstractClientBundleCallback(Class<? extends ClientBundle> wrappedClass, URL resourceURL) {
		this.wrappedClass = wrappedClass;
		this.resourceURL = resourceURL;
	}

	public Class<? extends ClientBundle> getWrappedClass() {
		return wrappedClass;
	}

	protected static String computeUrl(URL resourceURL, Class<? extends ClientBundle> proxiedClass) {
		//String fileSeparatorRegex = (File.separatorChar == '\\') ? "\\\\" : File.separator;
		//String packagePath = proxiedClass.getPackage().getName().replaceAll("\\.", fileSeparatorRegex);
		String packagePath = proxiedClass.getPackage().getName().replaceAll("\\.", "/");
		String resourceRelativePath = resourceURL.getPath().substring(resourceURL.getPath().indexOf(packagePath));
		//resourceRelativePath = resourceRelativePath.replaceAll(fileSeparatorRegex, "/");

		return GWT.getModuleBaseURL() + resourceRelativePath;
	}

}
