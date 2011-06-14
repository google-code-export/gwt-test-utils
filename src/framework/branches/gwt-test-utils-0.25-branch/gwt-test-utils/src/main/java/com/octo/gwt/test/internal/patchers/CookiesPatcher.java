package com.octo.gwt.test.internal.patchers;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Cookies;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(Cookies.class)
class CookiesPatcher {

  @PatchMethod
  public static void loadCookies(HashMap<String, String> m) {
  }

  @PatchMethod
  public static boolean needsRefresh() {
    return false;
  }

  @PatchMethod
  public static void removeCookieNative(String name) {
    Map<String, String> cachedCookies = getCookiesMap();
    cachedCookies.remove(name);
  }

  @PatchMethod
  public static void setCookieImpl(String name, String value, double expires,
      String domain, String path, boolean secure) {

    Map<String, String> cachedCookies = getCookiesMap();
    if (cachedCookies == null) {
      cachedCookies = new HashMap<String, String>();
      GwtReflectionUtils.setStaticField(Cookies.class, "cachedCookies",
          cachedCookies);
    }

    cachedCookies.put(name, value);
  }

  @PatchMethod
  public static String uriEncode(String s) {
    return s;
  }

  private static Map<String, String> getCookiesMap() {
    Map<String, String> cachedCookies = GwtReflectionUtils.getStaticFieldValue(
        Cookies.class, "cachedCookies");
    return cachedCookies;
  }

}
