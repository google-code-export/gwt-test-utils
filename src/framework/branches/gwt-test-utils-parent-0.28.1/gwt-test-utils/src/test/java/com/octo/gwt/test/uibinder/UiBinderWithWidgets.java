package com.octo.gwt.test.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UiBinderWithWidgets extends Composite {

  interface MyUiBinder extends UiBinder<Widget, UiBinderWithWidgets> {
  }

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  private final String uiFactoryLabelText;

  @UiField
  Button button;

  @UiField
  Image image;

  @UiField
  ListBox listBox;

  @UiField
  Label msgInnerWidget;

  @UiField
  Label msgLabel;

  @UiField(provided = true)
  ProvidedLabel providedLabel;

  @UiField
  PushButton pushButton;

  @UiField
  UiConstructorLabel uiConstructorLabel;

  @UiField
  UiFactoryLabel uiFactoryLabel;

  @UiField
  VerticalPanel verticalPanel;

  public UiBinderWithWidgets(String... names) {
    providedLabel = new ProvidedLabel("my provided string");
    this.uiFactoryLabelText = names[0];

    initWidget(uiBinder.createAndBindUi(this));
    for (String name : names) {
      listBox.addItem(name);
    }
  }

  @UiHandler("button")
  void handleClick(ClickEvent e) {
    listBox.setVisibleItemCount(2);
  }

  /** Used by MyUiBinder to instantiate UiFactoryLabel */
  @UiFactory
  UiFactoryLabel makeUiFactoryLabel() { // method name is insignificant
    return new UiFactoryLabel(uiFactoryLabelText);
  }

}
