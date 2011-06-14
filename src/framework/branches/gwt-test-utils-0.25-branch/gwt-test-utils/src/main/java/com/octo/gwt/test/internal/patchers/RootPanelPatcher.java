package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(RootPanel.class)
public class RootPanelPatcher {

  @PatchMethod
  public static com.google.gwt.user.client.Element getBodyElement() {
    return Document.get().getBody().cast();
  }

}
