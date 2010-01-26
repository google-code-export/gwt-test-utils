package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NodeFactory;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class DocumentPatcher extends AutomaticPatcher {

	private static int ID = 0;

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
