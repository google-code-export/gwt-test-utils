package com.octo.gwt.test17.internal.patcher;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NodeFactory;
import com.octo.gwt.test17.ng.AutomaticSubclasser;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.SubClassedHelper;

public class DocumentPatcher extends AutomaticSubclasser {

	private static int ID = 0;

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);
		
		cons.insertAfter(SubClassedHelper.getCodeSetProperty("this", "Body", NodeFactory.class.getCanonicalName() + ".createElement(\"body\")", false) + ";");
	}

	@PatchMethod
	public static String getCompatMode(Document document) {
		return "toto";
	}
	
	@PatchMethod
	public static Document get() {
		return NodeFactory.getDocument();
	}

	@PatchMethod
	public static String createUniqueId(Document document) {
		ID++;
		return "elem_" + Long.toString(ID);
	}

}
