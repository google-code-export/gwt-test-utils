package com.octo.gwt.test.internal.handlers;

import com.google.gwt.user.cellview.client.CellBasedWidgetImplSafari;
import com.octo.gwt.test.GwtCreateHandler;

/**
 * GwtCreateHandler for CellBasedWidgetImpl.
 * 
 * @author Gael Lazzari
 * 
 */
class CellBasedWidgetImplCreateHandler implements GwtCreateHandler {

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtCreateHandler#create(java.lang.Class)
   */
  public Object create(Class<?> classLiteral) throws Exception {
    if ("com.google.gwt.user.cellview.client.CellBasedWidgetImpl".equals(classLiteral.getName())) {
      return new CellBasedWidgetImplSafari();
    } else {
      return null;
    }
  }

}
