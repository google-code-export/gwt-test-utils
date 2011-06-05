package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.Method;
import java.net.URL;

import com.google.gwt.resources.client.ClientBundle;

class ImageResourceCallback extends ClientBundleCallback {

  private final String url;

  protected ImageResourceCallback(Class<? extends ClientBundle> wrappedClass,
      URL resourceURL, Class<? extends ClientBundle> proxiedClass) {
    super(wrappedClass);
    url = ResourceURLUtils.computeUrl(resourceURL, proxiedClass);
  }

  public Object call(Object proxy, Method method, Object[] args)
      throws Exception {
    if (method.getName().equals("getURL")) {
      return url;
    } else if (method.getName().equals("getHeight")) {
      return 0;
    } else if (method.getName().equals("getLeft")) {
      return 0;
    } else if (method.getName().equals("getWidth")) {
      return 0;
    } else if (method.getName().equals("getTop")) {
      return 0;
    } else if (method.getName().equals("isAnimated")) {
      return false;
    }

    return null;

  }

}
