package com.octo.gxt.test.internal.patchers;

import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(target = "com.extjs.gxt.ui.client.core.Ext")
class ExtPatcher {

  @PatchMethod
  static void loadDomHelper() {

  }

  @PatchMethod
  static void loadDomQuery() {

  }

  @PatchMethod
  static void loadExt() {

  }

  @PatchMethod
  static void loadFormat() {

  }

  @PatchMethod
  static void loadTemplate() {

  }

  @PatchMethod
  static void loadXTemplate() {

  }
}
