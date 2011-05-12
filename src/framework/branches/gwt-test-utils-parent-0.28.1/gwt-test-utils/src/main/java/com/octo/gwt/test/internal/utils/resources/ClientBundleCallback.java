package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.Method;
import java.net.URL;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.octo.gwt.test.exceptions.GwtTestResourcesException;

abstract class ClientBundleCallback {

  protected URL resourceURL;
  protected Class<? extends ClientBundle> wrappedClass;

  protected ClientBundleCallback(
      Class<? extends ClientBundle> wrappedClass, URL resourceURL) {
    this.wrappedClass = wrappedClass;
    this.resourceURL = resourceURL;
  }

  public abstract Object call(Object proxy, Method method, Object[] args)
      throws Exception;

  public Class<? extends ClientBundle> getWrappedClass() {
    return wrappedClass;
  }

  @SuppressWarnings("unchecked")
  protected String computeUrl(URL resourceURL,
      Class<? extends ClientBundle> proxiedClass) {
    // String fileSeparatorRegex = (File.separatorChar == '\\') ? "\\\\" :
    // File.separator;
    // String packagePath =
    // proxiedClass.getPackage().getName().replaceAll("\\.",
    // fileSeparatorRegex);
    String packagePath = proxiedClass.getPackage().getName().replaceAll("\\.",
        "/");
    int startIndex = resourceURL.getPath().indexOf(packagePath);
    if (startIndex == -1) {
      if (proxiedClass.getInterfaces() != null
          && proxiedClass.getInterfaces().length == 1) {
        return computeUrl(resourceURL,
            (Class<? extends ClientBundle>) proxiedClass.getInterfaces()[0]);
      } else {
        throw new GwtTestResourcesException(
            "Cannot compute the relative URL of resource '" + resourceURL + "'");
      }
    }
    String resourceRelativePath = resourceURL.getPath().substring(startIndex);

    return GWT.getModuleBaseURL() + resourceRelativePath;
  }
}
