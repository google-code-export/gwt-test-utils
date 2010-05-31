package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Document;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.PropertyContainerAwareHelper;

public class DocumentPatcher extends AutomaticPatcher {

	private static int ID = 0;
	
	public static final String BODY_PROPERTY = "Body";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);
		
		cons.insertAfter(PropertyContainerAwareHelper.getCodeSetProperty("this", BODY_PROPERTY, JavaScriptObjectFactory.class.getCanonicalName() + ".createElement(\"body\")") + ";");
	}

	@PatchMethod
	public static String getCompatMode(Document document) {
		return "toto";
	}
	
	@PatchMethod
	public static Document get() {
		return JavaScriptObjectFactory.getDocument();
	}

	@PatchMethod
	public static String createUniqueId(Document document) {
		ID++;
		return "elem_" + Long.toString(ID);
	}

}
