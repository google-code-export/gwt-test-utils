package com.octo.gwt.test.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class UiBinderWithWidgets extends Composite {

  interface MyUiBinder extends UiBinder<Widget, UiBinderWithWidgets> {
  }

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  Button button;

  @UiField
  Image image;

  @UiField
  ListBox listBox;

  @UiField(provided = true)
  ProvidedLabel providedLabel;

  @UiField
  PushButton pushButton;

  public UiBinderWithWidgets(String... names) {
    providedLabel = new ProvidedLabel("my provided string");

    initWidget(uiBinder.createAndBindUi(this));
    for (String name : names) {
      listBox.addItem(name);
    }
  }

  @UiHandler("button")
  void handleClick(ClickEvent e) {
    listBox.setVisibleItemCount(2);
  }

}
