package com.octo.gwt.test.internal.patchers;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(SafeHtmlBuilder.class)
public class SafeHtmlBuilderPatcher {

  @PatchMethod
  public static SafeHtmlBuilder appendHtmlConstant(SafeHtmlBuilder builder,
      String html) {
    // PatchMethod to avoid gwt-dev dependency.. See SafeHtmlHostedModeUtils
    StringBuilder sb = GwtReflectionUtils.getPrivateFieldValue(builder, "sb");
    sb.append(html);
    return builder;

  }

}
