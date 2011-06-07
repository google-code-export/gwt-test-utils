package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.Duration;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Duration.class)
public class DurationPatcher extends AutomaticPatcher {

  @PatchMethod
  public static double currentTimeMillis() {
    return System.currentTimeMillis();
  }

}
