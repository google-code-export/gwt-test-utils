package com.octo.gwt.test.internal.patchers;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FileUpload;
import com.octo.gwt.test.patchers.InitMethod;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.utils.JavassistUtils;

@PatchClass(FileUpload.class)
public class FileUploadPatcher {

  // @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  // public static String onBrowserEvent() {
  // return "return super.onBrowserEvent($1)";
  // }

  @InitMethod
  static void initClass(CtClass c) throws Exception {
    CtConstructor cons = JavassistUtils.findConstructor(c);

    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("super();");
    sb.append("setElement(").append(Document.class.getName()).append(
        ".get().createFileInputElement());");
    sb.append("setStyleName(\"gwt-FileUpload\");");
    sb.append("}");

    cons.setBody(sb.toString());

  }

}
