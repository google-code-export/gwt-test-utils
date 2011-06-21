package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.ButtonElement;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(ButtonElement.class)
class ButtonElementPatcher {

  @PatchMethod
  static String getType(ButtonElement buttonElement) {
    PropertyContainer properties = JavaScriptObjects.getObject(buttonElement,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getString(JsoProperties.ELEM_TYPE);
  }

}
