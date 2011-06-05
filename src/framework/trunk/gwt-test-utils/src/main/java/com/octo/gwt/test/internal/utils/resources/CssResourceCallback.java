package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.Method;
import java.net.URL;

import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.octo.gwt.test.internal.utils.resources.CssResourceReader.CssParsingResult;

class CssResourceCallback extends ClientBundleCallback {

  private static interface CssReader {

    CssParsingResult readCss() throws Exception;

    String readCssText() throws Exception;
  }

  private boolean alreadyInjected = false;

  private final CssReader cssReader;

  protected CssResourceCallback(Class<? extends ClientBundle> wrappedClass,
      final URL resourceURL) {
    super(wrappedClass);

    cssReader = new CssReader() {

      public CssParsingResult readCss() throws Exception {
        return CssResourceReader.readCss(resourceURL);
      }

      public String readCssText() throws Exception {
        return TextResourceReader.readFile(resourceURL);
      }

    };
  }

  public Object call(Object proxy, Method method, Object[] args)
      throws Exception {
    if (method.getName().equals("getText")) {
      return cssReader.readCss();
    } else if (method.getName().equals("ensureInjected")) {
      return ensureInjected();
    } else {
      return handleCustomMethod(method.getName());
    }

  }

  private boolean ensureInjected() throws Exception {
    if (!alreadyInjected) {
      StyleInjector.inject(cssReader.readCssText());
      alreadyInjected = true;
      return true;
    }
    return false;
  }

  private String handleCustomMethod(String methodName) throws Exception {
    CssParsingResult result = cssReader.readCss();
    String constant = result.getConstants().get(methodName);
    if (constant != null) {
      return constant;
    } else {
      return methodName;
    }
  }
}
