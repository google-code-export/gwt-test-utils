package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.xml.client.ProcessingInstruction;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(target = "com.google.gwt.xml.client.impl.ProcessingInstructionImpl")
class ProcessingInstructionImplPatcher {

  @PatchMethod
  static String toString(ProcessingInstruction processingInstruction) {
    JavaScriptObject jso = GwtReflectionUtils.getPrivateFieldValue(
        processingInstruction, "jsObject");
    return jso.toString();
  }

}
