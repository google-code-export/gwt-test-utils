package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.core.XTemplate;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(XTemplate.class)
class XTemplatePatcher {

  @PatchMethod
  static XTemplate create(String html) {
    return GwtReflectionUtils.instantiateClass(XTemplate.class);
  }
}
