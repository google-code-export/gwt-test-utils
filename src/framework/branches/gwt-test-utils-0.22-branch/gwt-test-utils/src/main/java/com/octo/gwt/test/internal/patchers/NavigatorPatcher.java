package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Window.Navigator;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Navigator.class)
public class NavigatorPatcher extends AutomaticPatcher {

  @PatchMethod
  public static String getAppCodeName() {
    return "gwt-test-utils fake navigator-codeName";
  }

  @PatchMethod
  public static String getAppName() {
    return "gwt-test-utils fake navigator";
  }

  @PatchMethod
  public static String getAppVersion() {
    return "gwt-test-utils-1.0.0";
  }

  @PatchMethod
  public static String getPlatform() {
    return "only the JVM @gwt-test-utils";
  }

  @PatchMethod
  public static String getUserAgent() {
    return "gwt-test-utils fake navigator user-agent";
  }

  @PatchMethod
  public static boolean isJavaEnabled() {
    return true;
  }

}
