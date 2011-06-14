package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.Duration;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Duration.class)
public class DurationPatcher {

  @PatchMethod
  public static double currentTimeMillis() {
    return System.currentTimeMillis();
  }

  @PatchMethod
  public static int uncheckedConversion(double elapsed) {
    return (int) elapsed;
  }

}
