package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.Window.Location;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(Location.class)
public class LocationPatcher extends AutomaticPatcher {

  @PatchMethod
  public static String getProtocol() {
    return "http";
  }

  @PatchMethod
  public static String getPort() {
    return "80";
  }

}
