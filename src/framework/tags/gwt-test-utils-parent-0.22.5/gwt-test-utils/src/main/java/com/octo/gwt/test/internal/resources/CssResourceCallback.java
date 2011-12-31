package com.octo.gwt.test.internal.resources;

import java.lang.reflect.Method;
import java.net.URL;

import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.CssResource;
import com.octo.gwt.test.internal.resources.CssResourceReader.CssParsingResult;

/**
 * Callback interface where {@link CssResource } methods calls are redirected.
 * <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
class CssResourceCallback implements ResourcePrototypeCallback {

  private static interface CssReader {

    CssParsingResult readCss() throws Exception;

    String readCssText() throws Exception;
  }

  private boolean alreadyInjected = false;

  private final CssReader cssReader;

  CssResourceCallback(final String text) {

    cssReader = new CssReader() {

      public CssParsingResult readCss() throws Exception {
        return CssResourceReader.get().readCss(text);
      }

      public String readCssText() throws Exception {
        return text;
      }

    };
  }

  CssResourceCallback(final URL resourceURL) {

    cssReader = new CssReader() {

      public CssParsingResult readCss() throws Exception {
        return CssResourceReader.get().readCss(resourceURL);
      }

      public String readCssText() throws Exception {
        return TextResourceReader.get().readFile(resourceURL);
      }

    };
  }

  public Object call(Method method, Object[] args) throws Exception {
    if (method.getName().equals("getText")) {
      return cssReader.readCssText();
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
