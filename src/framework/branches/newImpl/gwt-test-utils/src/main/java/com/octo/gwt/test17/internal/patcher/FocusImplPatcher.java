package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.SubClassedHelper;

public class FocusImplPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void blur(FocusImpl focusImpl, Element element) {
		
	}
	
	@PatchMethod
	public static void focus(FocusImpl focusImpl, Element element) {
		
	}

	@PatchMethod
	public static Element createFocusable(FocusImpl focusImpl) {
		DivElement div = Document.get().createDivElement();
		return ElementUtils.castToUserElement(div);
	}

	@PatchMethod
	public static int getTabIndex(FocusImpl focusImpl, Element elem) {
		return SubClassedHelper.getPropertyInteger(elem, "TabIndex");
	}
	
	@PatchMethod
	public static void setTabIndex(FocusImpl focusImpl, Element elem, int index) {
		SubClassedHelper.setProperty(elem, "TabIndex", index);
	}

}
