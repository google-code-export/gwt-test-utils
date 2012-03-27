package com.googlecode.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.core.XTemplate;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

@PatchClass(XTemplate.class)
class XTemplatePatcher {

  @PatchMethod
  static XTemplate create(String html) {
    return GwtReflectionUtils.instantiateClass(XTemplate.class);
  }
}
