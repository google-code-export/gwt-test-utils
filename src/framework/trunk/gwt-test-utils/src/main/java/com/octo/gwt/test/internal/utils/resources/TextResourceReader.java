package com.octo.gwt.test.internal.utils.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TextResourceReader {

  private static Map<URL, String> cache;

  public static String readFile(URL url) throws UnsupportedEncodingException,
      IOException {
    if (cache == null) {
      cache = new HashMap<URL, String>();
    }

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

  public static void reset() {
    cache.clear();
  }

}
