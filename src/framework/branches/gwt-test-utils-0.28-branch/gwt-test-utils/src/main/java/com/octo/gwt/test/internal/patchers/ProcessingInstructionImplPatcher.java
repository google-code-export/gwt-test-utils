package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.xml.client.ProcessingInstruction;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(classes = {"com.google.gwt.xml.client.impl.ProcessingInstructionImpl"})
public class ProcessingInstructionImplPatcher extends AutomaticPatcher {

  @PatchMethod
  public static String toString(ProcessingInstruction processingInstruction) {
    JavaScriptObject jso = GwtReflectionUtils.getPrivateFieldValue(
        processingInstruction, "jsObject");
    return jso.toString();
  }

}
