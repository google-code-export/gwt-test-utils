package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(TextBoxImpl.class)
public class TextBoxImplPatcher {

  @PatchMethod
  public static int getCursorPos(TextBoxImpl textBoxImpl, Element e) {
    return JavaScriptObjects.getInteger(e, JsoProperties.SELECTION_START);
  }

  @PatchMethod
  public static int getSelectionLength(TextBoxImpl textBoxImpl, Element e) {
    int selectionStart = JavaScriptObjects.getInteger(e,
        JsoProperties.SELECTION_START);
    int selectionEnd = JavaScriptObjects.getInteger(e,
        JsoProperties.SELECTION_END);
    return selectionEnd - selectionStart;
  }

  @PatchMethod
  public static void setSelectionRange(TextBoxImpl textBoxImpl, Element e,
      int pos, int length) {
    JavaScriptObjects.setProperty(e, JsoProperties.SELECTION_START, pos);
    JavaScriptObjects.setProperty(e, JsoProperties.SELECTION_END, pos + length);
  }

}
