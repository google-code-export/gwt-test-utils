package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.util.Format;
import com.octo.gwt.test.internal.utils.GwtStringUtils;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Format.class)
public class FormatPatcher {

  @PatchMethod
  public static String camelize(String s) {
    return GwtStringUtils.camelize(s);
  }

  @PatchMethod
  public static String hyphenize(String name) {
    return GwtStringUtils.hyphenize(name);
  }

}
