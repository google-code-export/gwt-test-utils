package com.octo.gwt.test.uibinder;

import com.google.gwt.user.client.ui.Label;

public class ProvidedLabel extends Label {

  private String customText;
  String myString;

  ProvidedLabel(String myString) {
    this.myString = myString;
  }

  public String getCustomText() {
    return customText;
  }

  public void setCustomText(String customText) {
    this.customText = customText;
  }

}
