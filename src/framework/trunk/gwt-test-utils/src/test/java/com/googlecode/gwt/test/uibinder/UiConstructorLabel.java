package com.googlecode.gwt.test.uibinder;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Label;

public class UiConstructorLabel extends Label {

   private String customText;
   final String uiConstructorLabel;

   @UiConstructor
   UiConstructorLabel(String uiConstructorLabel) {
      this.uiConstructorLabel = uiConstructorLabel;
   }

   public String getCustomText() {
      return customText;
   }

   public void setCustomText(String customText) {
      this.customText = customText;
   }

}
