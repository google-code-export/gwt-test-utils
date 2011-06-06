package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(TextBoxImpl.class)
public class TextBoxImplPatcher extends AutomaticPatcher {

  private static final String SELECTION_START = "SelectionStart";
  private static final String SELECTION_END = "SelectionEnd";

  @PatchMethod
  public static void setSelectionRange(TextBoxImpl textBoxImpl, Element e,
      int pos, int length) {
    PropertyContainerHelper.setProperty(e, SELECTION_START, pos);
    PropertyContainerHelper.setProperty(e, SELECTION_END, pos + length);
  }

  @PatchMethod
  public static int getCursorPos(TextBoxImpl textBoxImpl, Element e) {
    return PropertyContainerHelper.getPropertyInteger(e, SELECTION_START);
  }

  @PatchMethod
  public static int getSelectionLength(TextBoxImpl textBoxImpl, Element e) {
    int selectionStart = PropertyContainerHelper.getPropertyInteger(e,
        SELECTION_START);
    int selectionEnd = PropertyContainerHelper.getPropertyInteger(e,
        SELECTION_END);
    return selectionEnd - selectionStart;
  }

}
