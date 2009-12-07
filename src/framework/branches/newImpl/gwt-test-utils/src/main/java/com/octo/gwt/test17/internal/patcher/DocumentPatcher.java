package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NodeFactory;

public class DocumentPatcher extends AbstractPatcher {

	private static Document DOCUMENT = NodeFactory.createDocument();

	@Override
	public boolean patchMethod(CtMethod m) throws Exception {
		if ("get".equals(m.getName())) {
			replaceImplementation(m, "get", null);
		} else {
			return false;
		}

		return true;

	}

	public static Document get() {
		return DOCUMENT;
	}

}
