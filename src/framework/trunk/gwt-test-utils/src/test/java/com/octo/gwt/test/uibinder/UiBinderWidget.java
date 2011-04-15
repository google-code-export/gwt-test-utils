package com.octo.gwt.test.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class UiBinderWidget extends Composite {

  interface MyUiBinder extends UiBinder<Widget, UiBinderWidget> {
  }

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  ListBox listBox;

  public UiBinderWidget(String... names) {
    // sets listBox
    initWidget(uiBinder.createAndBindUi(this));
    for (String name : names) {
      listBox.addItem(name);
    }
  }

}
