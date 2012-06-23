package com.googlecode.gwt.test.internal.handlers;

import com.googlecode.gwt.test.GwtCreateHandler;

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
   * @see com.googlecode.gwt.test.GwtCreateHandler#create(java.lang.Class)
   */
  public Object create(Class<?> classLiteral) throws Exception {
    if ("com.google.gwt.user.cellview.client.CellBasedWidgetImpl".equals(classLiteral.getName())) {
      return Class.forName(
          "com.google.gwt.user.cellview.client.CellBasedWidgetImplStandard").newInstance();
    } else {
      return null;
    }
  }

}
