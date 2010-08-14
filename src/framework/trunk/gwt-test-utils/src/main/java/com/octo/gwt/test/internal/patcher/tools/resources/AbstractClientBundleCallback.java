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

	@SuppressWarnings("unchecked")
	protected String computeUrl(URL resourceURL, Class<? extends ClientBundle> proxiedClass) {
		//String fileSeparatorRegex = (File.separatorChar == '\\') ? "\\\\" : File.separator;
		//String packagePath = proxiedClass.getPackage().getName().replaceAll("\\.", fileSeparatorRegex);
		String packagePath = proxiedClass.getPackage().getName().replaceAll("\\.", "/");
		int startIndex = resourceURL.getPath().indexOf(packagePath);
		if (startIndex == -1) {
			if (proxiedClass.getInterfaces() != null && proxiedClass.getInterfaces().length == 1) {
				return computeUrl(resourceURL, (Class<? extends ClientBundle>) proxiedClass.getInterfaces()[0]);
			} else {
				throw new RuntimeException("Cannot compute the relative URL of resource '" + resourceURL + "'");
			}
		}
		String resourceRelativePath = resourceURL.getPath().substring(startIndex);

		return GWT.getModuleBaseURL() + resourceRelativePath;
	}
}
