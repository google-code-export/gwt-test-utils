package com.octo.gwt.test.internal.patcher.tools.resources;

import java.io.File;

import com.google.gwt.core.client.GWT;
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
	
	protected static String computeUrl(File resourceFile, Class<? extends ClientBundle> proxiedClass) {
		String fileSeparatorRegex = (File.separatorChar == '\\') ? "\\\\" : File.separator;
		String packagePath = proxiedClass.getPackage().getName().replaceAll("\\.", fileSeparatorRegex);
		String resourceRelativePath = resourceFile.getPath().substring(resourceFile.getPath().indexOf(packagePath));
		resourceRelativePath = resourceRelativePath.replaceAll(fileSeparatorRegex, "/");
		
		return GWT.getModuleBaseURL() + resourceRelativePath;
	}

}
