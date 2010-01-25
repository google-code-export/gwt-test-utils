package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NodeFactory;

public class DocumentPatcher extends AbstractPatcher {

	private static int ID = 0;

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "get")) {
			return callMethod("get");
		} else if (match(m, "createUniqueId")) {
			return callMethod("createUniqueId");
		} else if (match(m, "getCompatMode")) {
			return "return \"toto\"";
		}

		return null;

	}

	public static Document get() {
		return NodeFactory.getDocument();
	}

	public static String createUniqueId() {
		ID++;
		return "elem_" + Long.toString(ID);
	}

}
