package com.octo.gwt.test.internal.patchers;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FileUpload;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.patchers.PatchMethod.Type;

@PatchClass(FileUpload.class)
public class FileUploadPatcher extends AutomaticPatcher {

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("super();");
		sb.append("setElement(").append(Document.class.getName()).append(".get().createFileInputElement());");
		sb.append("setStyleName(\"gwt-FileUpload\");");
		sb.append("}");

		cons.setBody(sb.toString());

	}

	@PatchMethod(type = Type.NEW_CODE_AS_STRING)
	public static String onBrowserEvent() {
		return "return super.onBrowserEvent($1)";
	}

}
