package com.octo.gwt.test.internal.patchers;

import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(target = "com.google.gwt.event.dom.client.TouchEvent$TouchSupportDetector")
class TouchSupportDetectorPatcher {

  @PatchMethod
  static boolean detectTouchSupport(Object touchSupportDetector) {
    return false;
  }

}
