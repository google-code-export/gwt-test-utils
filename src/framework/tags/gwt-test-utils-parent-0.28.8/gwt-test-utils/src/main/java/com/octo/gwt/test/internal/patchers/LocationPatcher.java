package com.octo.gwt.test.internal.patchers;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.internal.AfterTestCallback;
import com.octo.gwt.test.internal.AfterTestCallbackManager;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Location.class)
class LocationPatcher {

  /**
   * Holder for a resetable URL
   * 
   */
  private static class UrlHolder implements AfterTestCallback {

    private URL url;

    private UrlHolder() {
      AfterTestCallbackManager.get().registerCallback(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.octo.gwt.test.internal.AfterTestCallback#afterTest()
     */
    public void afterTest() throws Throwable {
      url = null;
    }
  }

  private static UrlHolder urlHolder = new UrlHolder();

  @PatchMethod
  static void assign(String newURL) {
    try {
      urlHolder.url = new URL(newURL);
    } catch (MalformedURLException e) {
      GWT.log("Failed to assign new URL '" + newURL + "'", e);
    }
  }

  @PatchMethod
  static String getHost() {
    URL urlToUse = getURLToUse();
    return urlToUse.getHost() + ":" + getPort(urlToUse);
  }

  @PatchMethod
  static String getHostName() {
    return getURLToUse().getHost();
  }

  @PatchMethod
  static String getHref() {
    return getURLToUse().toString();
  }

  @PatchMethod
  static String getPort() {
    return getPort(getURLToUse());
  }

  @PatchMethod
  static String getProtocol() {
    return getURLToUse().getProtocol();
  }

  @PatchMethod
  static void replace(String newURL) {
    assign(newURL);
  }

  private static String getPort(URL url) {
    int port = url.getPort();

    return port != -1 ? String.valueOf(getURLToUse().getPort()) : "80";
  }

  private static URL getURLToUse() {
    try {
      return urlHolder.url != null ? urlHolder.url : new URL(
          GWT.getModuleBaseURL());
    } catch (MalformedURLException e) {
      throw new GwtTestConfigurationException(
          "GWT.getHostPageBaseURL() has failed to be parsed in a "
              + URL.class.getName() + " instance", e);
    }
  }

}
