package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.octo.gwt.test.internal.patchers.dom.DOMProperties;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(TextBoxImpl.class)
public class TextBoxImplPatcher extends AutomaticPatcher {

  @PatchMethod
  public static int getCursorPos(TextBoxImpl textBoxImpl, Element e) {
    return PropertyContainerUtils.getPropertyInteger(e,
        DOMProperties.SELECTION_START);
  }

  @PatchMethod
  public static int getSelectionLength(TextBoxImpl textBoxImpl, Element e) {
    int selectionStart = PropertyContainerUtils.getPropertyInteger(e,
        DOMProperties.SELECTION_START);
    int selectionEnd = PropertyContainerUtils.getPropertyInteger(e,
        DOMProperties.SELECTION_END);
    return selectionEnd - selectionStart;
  }

  @PatchMethod
  public static void setSelectionRange(TextBoxImpl textBoxImpl, Element e,
      int pos, int length) {
    PropertyContainerUtils.setProperty(e, DOMProperties.SELECTION_START, pos);
    PropertyContainerUtils.setProperty(e, DOMProperties.SELECTION_END, pos
        + length);
  }

}
