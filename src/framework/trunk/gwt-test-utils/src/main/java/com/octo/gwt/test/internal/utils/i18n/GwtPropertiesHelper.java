package com.octo.gwt.test.internal.utils.i18n;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.octo.gwt.test.exceptions.GwtTestI18NException;
import com.octo.gwt.test.internal.utils.GwtPropertiesUtils;

public class GwtPropertiesHelper {

  private static final GwtPropertiesHelper INSTANCE = new GwtPropertiesHelper();

  public static GwtPropertiesHelper get() {
    return INSTANCE;
  }

  private final Map<String, Properties> cachedProperties = new HashMap<String, Properties>();

  private GwtPropertiesHelper() {
  }

  public Properties getLocalizedProperties(String prefix, Locale locale) {
    if (locale != null) {
      prefix += ("_" + locale.toString());
    }
    return getProperties(prefix);
  }

  public Properties getProperties(String path) {
    Properties properties = cachedProperties.get(path);

    if (properties != null) {
      return properties;
    }
    String propertiesNameFile = "/" + path + ".properties";
    InputStream inputStream = path.getClass().getResourceAsStream(
        propertiesNameFile);
    if (inputStream == null) {
      return null;
    }
    try {
      properties = new Properties();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
          "UTF-8");
      GwtPropertiesUtils.load(properties, inputStreamReader);
      cachedProperties.put(path, properties);
      return properties;
    } catch (Exception e) {
      throw new GwtTestI18NException("Unable to load property file [" + path
          + "]", e);
    }
  }

  public void reset() {
    cachedProperties.clear();
  }

}
