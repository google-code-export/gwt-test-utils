package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.util.Util;
import com.octo.gwt.test.internal.utils.GwtStringUtils;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Util.class)
public class UtilPatcher {

  @PatchMethod
  public static int parseInt(String value, int defaultValue) {
    return GwtStringUtils.parseInt(value, defaultValue);
  }

}
