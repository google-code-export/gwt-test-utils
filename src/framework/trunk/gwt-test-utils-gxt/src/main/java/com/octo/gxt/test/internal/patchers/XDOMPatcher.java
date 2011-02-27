package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.core.XDOM;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(XDOM.class)
public class XDOMPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element getBody() {
		return Document.get().getBody().cast();
	}

	@PatchMethod
	public static String getComputedStyle(Element e, String style) {
		return "";
	}

}
