package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.Method;
import java.net.URL;

import com.google.gwt.resources.client.ClientBundle;

class TextResourceCallback extends ClientBundleCallback {

  private static interface TextReader {

    String readText() throws Exception;
  }

  private final TextReader textReader;

  protected TextResourceCallback(Class<? extends ClientBundle> wrappedClass,
      final URL resourceURL) {
    super(wrappedClass);
    textReader = new TextReader() {

      public String readText() throws Exception {
        return TextResourceReader.readFile(resourceURL);
      }

    };
  }

  public Object call(Object proxy, Method method, Object[] args)
      throws Exception {
    if (method.getName().equals("getText")) {
      return textReader.readText();
    }

    return null;

  }

}
