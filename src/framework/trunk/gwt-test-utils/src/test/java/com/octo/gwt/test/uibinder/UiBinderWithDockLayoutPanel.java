package com.octo.gwt.test.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UiBinderWithDockLayoutPanel extends Composite {

  interface UiBinderWithDocLayoutPanelUiBinder extends
      UiBinder<Widget, UiBinderWithDockLayoutPanel> {
  }

  private static UiBinderWithDocLayoutPanelUiBinder uiBinder = GWT.create(UiBinderWithDocLayoutPanelUiBinder.class);

  @UiField
  Label centerLabel;

  @UiField
  Label eastLabel;

  @UiField
  Label northLabel;

  @UiField
  Label southLabel;

  @UiField
  HTML westHTML;

  public UiBinderWithDockLayoutPanel() {
    initWidget(uiBinder.createAndBindUi(this));

    System.out.println("northLabel : " + northLabel.getText());
    System.out.println("centerLabel : " + centerLabel.getText());
    System.out.println("westHTML : " + westHTML.getHTML());
    System.out.println("---");
    System.out.println("eastLabel : " + eastLabel.getText());
    System.out.println("southLabel : " + southLabel.getText());
  }

}
