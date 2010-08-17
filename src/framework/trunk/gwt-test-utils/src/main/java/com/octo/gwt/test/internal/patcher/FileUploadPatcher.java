package com.octo.gwt.test.internal.patcher;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.FileUpload;
import com.octo.gwt.test.internal.utils.ElementUtils;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.patcher.PatchType;

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

	@PatchMethod(PatchType.NEW_CODE_AS_STRING)
	public static String onBrowserEvent() {
		return "return super.onBrowserEvent($1)";
	}

	@PatchMethod
	public static InputElement getInputElement(FileUpload fileUpload) {
		return ElementUtils.castToDomElement(fileUpload.getElement());
	}

}
