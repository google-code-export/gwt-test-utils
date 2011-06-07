package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.core.XTemplate;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(XTemplate.class)
public class XTemplatePatcher extends AutomaticPatcher {

  @PatchMethod
  public static XTemplate create(String html) {
    return GwtReflectionUtils.instantiateClass(XTemplate.class);
  }
}
