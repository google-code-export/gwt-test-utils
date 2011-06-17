package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.Method;
import java.net.URL;

import com.google.gwt.resources.client.TextResource;

/**
 * Callback interface where {@link TextResource } methods calls are redirected.
 * <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
class TextResourceCallback implements ResourcePrototypeCallback {

  private static interface TextReader {

    String readText() throws Exception;
  }

  private final TextReader textReader;

  TextResourceCallback(final String text) {
    textReader = new TextReader() {

      public String readText() throws Exception {
        return text;
      }

    };
  }

  TextResourceCallback(final URL resourceURL) {
    textReader = new TextReader() {

      public String readText() throws Exception {
        return TextResourceReader.get().readFile(resourceURL);
      }

    };
  }

  public Object call(Method method, Object[] args) throws Exception {
    if (method.getName().equals("getText")) {
      return textReader.readText();
    }

    return null;

  }

}
