package com.octo.gwt.test.internal.utils.resources;

import java.net.URL;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.exceptions.GwtTestResourcesException;

class ResourceURLUtils {

  public static String computeUrl(URL resourceURL, Class<?> proxiedClass) {
    String packagePath = proxiedClass.getPackage().getName().replaceAll("\\.",
        "/");
    int startIndex = resourceURL.getPath().indexOf(packagePath);
    if (startIndex == -1) {
      if (proxiedClass.getInterfaces() != null
          && proxiedClass.getInterfaces().length == 1) {
        return computeUrl(resourceURL, proxiedClass.getInterfaces()[0]);
      } else {
        throw new GwtTestResourcesException(
            "Cannot compute the relative URL of resource '" + resourceURL + "'");
      }
    }
    String resourceRelativePath = resourceURL.getPath().substring(startIndex);

    return GWT.getModuleBaseURL() + resourceRelativePath;
  }

  private ResourceURLUtils() {

  }

}
