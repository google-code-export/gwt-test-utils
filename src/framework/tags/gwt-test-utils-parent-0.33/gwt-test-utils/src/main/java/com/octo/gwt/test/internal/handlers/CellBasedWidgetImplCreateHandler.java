package com.octo.gwt.test.internal.handlers;

import com.google.gwt.user.cellview.client.CellBasedWidgetImplStandardBase;
import com.octo.gwt.test.GwtCreateHandler;

class CellBasedWidgetImplCreateHandler implements GwtCreateHandler {

  public Object create(Class<?> classLiteral) throws Exception {
    if ("com.google.gwt.user.cellview.client.CellBasedWidgetImpl".equals(classLiteral.getName())) {
      return new CellBasedWidgetImplStandardBase();
    } else {
      return null;
    }
  }

}
