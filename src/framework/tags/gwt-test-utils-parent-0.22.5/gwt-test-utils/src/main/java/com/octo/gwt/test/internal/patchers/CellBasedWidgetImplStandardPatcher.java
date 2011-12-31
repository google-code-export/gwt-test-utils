package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Element;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(target = "com.google.gwt.user.cellview.client.CellBasedWidgetImplStandard")
class CellBasedWidgetImplStandardPatcher {

  @PatchMethod
  static void initEventSystem(Object cellBasedWidgetImplStandard) {

  }

  @PatchMethod
  static void sinkEventImpl(Object cellBasedWidgetImplStandard, Element elem,
      String typeName) {

  }

}
