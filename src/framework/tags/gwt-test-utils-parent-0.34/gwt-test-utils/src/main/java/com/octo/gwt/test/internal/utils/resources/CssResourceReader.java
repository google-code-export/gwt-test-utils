package com.octo.gwt.test.internal.utils.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.octo.gwt.test.internal.AfterTestCallback;
import com.octo.gwt.test.internal.AfterTestCallbackManager;

/**
 * Utility class to parse CSS files.<strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
class CssResourceReader implements AfterTestCallback {

  public static class CssParsingResult {

    private final Map<String, String> constants;

    private CssParsingResult(Map<String, String> constants, Set<String> styles) {
      this.constants = constants;
    }

    public Map<String, String> getConstants() {
      return constants;
    }
  }

  private static final Pattern CSS_CONSTANT_PATTERN = Pattern.compile("^\\s*@def (\\S+)\\s+(\\S+)\\s*$");

  private static final CssResourceReader INSTANCE = new CssResourceReader();

  public static CssResourceReader get() {
    return INSTANCE;
  }

  private final Map<URL, CssParsingResult> cache;

  private CssResourceReader() {
    cache = new HashMap<URL, CssParsingResult>();
    AfterTestCallbackManager.get().registerCallback(this);
  }

  public void afterTest() throws Throwable {
    cache.clear();
  }

  public CssParsingResult readCss(String text) throws IOException {
    return parse(new StringReader(text));
  }

  public CssParsingResult readCss(URL url) throws IOException {

    CssParsingResult parsingResult = cache.get(url);
    if (parsingResult == null) {
      parsingResult = parse(new InputStreamReader(url.openStream(), "UTF-8"));
      cache.put(url, parsingResult);
    }

    return parsingResult;
  }

  private CssParsingResult parse(Reader reader) throws IOException {

    Map<String, String> constants = new HashMap<String, String>();
    Set<String> styles = new HashSet<String>();

    BufferedReader br = null;

    try {
      br = new BufferedReader(reader);

      String line;
      Matcher m;
      while ((line = br.readLine()) != null) {
        m = CSS_CONSTANT_PATTERN.matcher(line);
        if (m.matches()) {
          constants.put(m.group(1), m.group(2));
        }
      }

      return new CssParsingResult(constants, styles);

    } finally {
      if (br != null) {
        br.close();
      }
    }
  }

}
