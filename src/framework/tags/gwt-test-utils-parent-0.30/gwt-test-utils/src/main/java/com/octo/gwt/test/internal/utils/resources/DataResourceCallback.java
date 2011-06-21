package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.Method;

import com.google.gwt.resources.client.DataResource;

/**
 * Callback interface where {@link DataResource } methods calls are redirected.
 * <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
class DataResourceCallback implements ResourcePrototypeCallback {

  private final String url;

  DataResourceCallback(String url) {
    this.url = url;
  }

  public Object call(Method method, Object[] args) throws Exception {
    if (method.getName().equals("getUrl")) {
      return url;
    }

    return null;

  }

}
