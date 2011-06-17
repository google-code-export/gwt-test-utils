package com.octo.gxt.test.internal.patchers;

import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(target = "com.extjs.gxt.ui.client.core.Ext")
public class ExtPatcher {

  @PatchMethod
  public static void loadDomHelper() {

  }

  @PatchMethod
  public static void loadDomQuery() {

  }

  @PatchMethod
  public static void loadExt() {

  }

  @PatchMethod
  public static void loadFormat() {

  }

  @PatchMethod
  public static void loadTemplate() {

  }

  @PatchMethod
  public static void loadXTemplate() {

  }
}
