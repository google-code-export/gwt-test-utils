package com.googlecode.gwt.test.uibinder;

import com.google.gwt.user.client.ui.Label;

public class ProvidedLabel extends Label {

   private String customText;
   final String providedString;

   ProvidedLabel(String providedString) {
      this.providedString = providedString;
   }

   public String getCustomText() {
      return customText;
   }

   public void setCustomText(String customText) {
      this.customText = customText;
   }

}
