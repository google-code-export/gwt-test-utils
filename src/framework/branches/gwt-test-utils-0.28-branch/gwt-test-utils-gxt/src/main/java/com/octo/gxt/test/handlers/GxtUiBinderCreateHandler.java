package com.octo.gxt.test.handlers;

import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.internal.uibinder.UiBinderCreateHandler;

/**
 * A custom <code>GwtCreateHandler</code> to handle GXT component creations
 * through the third-party library <strong>gxt-uibinder</strong>
 * (http://code.google.com/p/gxt-uibinder/)
 * 
 * @author Gael Lazzari
 * 
 */
public class GxtUiBinderCreateHandler implements GwtCreateHandler {

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtCreateHandler#create(java.lang.Class)
   */
  public Object create(Class<?> classLiteral) throws Exception {
    return UiBinderCreateHandler.get().create(classLiteral);
  }

}
