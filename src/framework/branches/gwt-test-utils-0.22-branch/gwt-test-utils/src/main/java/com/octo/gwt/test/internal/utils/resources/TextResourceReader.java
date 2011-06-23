package com.octo.gwt.test.internal.utils.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.octo.gwt.test.internal.AfterTestCallback;
import com.octo.gwt.test.internal.AfterTestCallbackManager;

/**
 * Utility class to parse text files.<strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
class TextResourceReader implements AfterTestCallback {

  private static final TextResourceReader INSTANCE = new TextResourceReader();

  public static TextResourceReader get() {
    return INSTANCE;
  }

  private final Map<URL, String> cache;

  private TextResourceReader() {
    cache = new HashMap<URL, String>();
    AfterTestCallbackManager.get().registerCallback(this);
  }

  public void afterTest() throws Throwable {
    cache.clear();
  }

  public String readFile(URL url) throws UnsupportedEncodingException,
      IOException {

    if (!cache.containsKey(url)) {
      StringBuilder sb = new StringBuilder();

      BufferedReader reader = null;

      try {
        reader = new BufferedReader(new InputStreamReader(url.openStream(),
            "UTF-8"));

        String line;
        while ((line = reader.readLine()) != null) {
          sb.append(line).append("\r\n");
        }

        if (sb.length() > 0) {
          sb.delete(sb.length() - "\r\n".length(), sb.length());
        }
        cache.put(url, sb.toString());
      } finally {
        if (reader != null) {
          reader.close();
        }
      }
    }

    return cache.get(url);
  }

}
