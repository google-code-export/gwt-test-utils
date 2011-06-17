package com.octo.gwt.test.internal.handlers;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.octo.gwt.test.GwtCreateHandler;

public class HTMLPanelCreateHandler implements GwtCreateHandler {

  public Object create(Class<?> classLiteral) throws Exception {
    return HTMLPanel.class == classLiteral ? new HTMLPanel("") : null;
  }

}
