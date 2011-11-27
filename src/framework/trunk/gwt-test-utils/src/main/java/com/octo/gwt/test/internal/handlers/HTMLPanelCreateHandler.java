package com.octo.gwt.test.internal.handlers;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.octo.gwt.test.GwtCreateHandler;

/**
 * GwtCreateHandler for {@link HTMLPanel}.
 * 
 * @author Gael Lazzari
 * 
 */
class HTMLPanelCreateHandler implements GwtCreateHandler {

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtCreateHandler#create(java.lang.Class)
   */
  public Object create(Class<?> classLiteral) throws Exception {
    return HTMLPanel.class == classLiteral ? new HTMLPanel("") : null;
  }

}
