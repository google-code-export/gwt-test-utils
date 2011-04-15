package com.octo.gwt.test.internal.uibinder.objects;

import com.google.gwt.user.client.ui.HTMLPanel;

public class UiBinderHTMLPanel extends UiBinderWidget<HTMLPanel> {

  public UiBinderHTMLPanel() {
    super(HTMLPanel.class);
  }

  @Override
  protected HTMLPanel instanciate(Class<HTMLPanel> clazz) {
    return new HTMLPanel("");
  }

}
