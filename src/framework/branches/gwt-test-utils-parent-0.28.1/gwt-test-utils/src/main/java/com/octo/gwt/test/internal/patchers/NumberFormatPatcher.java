package com.octo.gwt.test.internal.patchers;

import java.math.BigDecimal;

import com.google.gwt.i18n.client.NumberFormat;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(NumberFormat.class)
public class NumberFormatPatcher extends AutomaticPatcher {

  @PatchMethod
  public static String toPrecision(double d, int digits) {
    return new BigDecimal(d).setScale(digits, BigDecimal.ROUND_DOWN).toString();
  }

}
