package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(RowFormatter.class)
class HTMLTableRowFormatterPatcher {

  @PatchMethod
  static Element getRow(RowFormatter rowFormatter, Element elem, int row) {
    return elem.getChildNodes().getItem(row).cast();
  }

}
