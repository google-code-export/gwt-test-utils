package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Window.Location;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Location.class)
class LocationPatcher {

  @PatchMethod
  static String getPort() {
    return "80";
  }

  @PatchMethod
  static String getProtocol() {
    return "http";
  }

}
