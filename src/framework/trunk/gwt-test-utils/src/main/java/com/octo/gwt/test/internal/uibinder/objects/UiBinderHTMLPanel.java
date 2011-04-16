package com.octo.gwt.test.internal.uibinder.objects;

import org.xml.sax.Attributes;

import com.google.gwt.user.client.ui.HTMLPanel;

public class UiBinderHTMLPanel extends UiBinderWidget<HTMLPanel> {

  public UiBinderHTMLPanel(Attributes attributes, Object owner) {
    super(HTMLPanel.class, attributes, owner);
  }

  @Override
  protected HTMLPanel instanciate(Class<HTMLPanel> clazz) {
    return new HTMLPanel("");
  }

}
