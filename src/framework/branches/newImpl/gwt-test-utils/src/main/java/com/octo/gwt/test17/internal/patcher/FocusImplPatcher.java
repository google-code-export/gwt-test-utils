package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.ElementWrapper;
import com.octo.gwt.test17.internal.patcher.dom.PropertyHolder;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.SubClassedObject;

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
		if (elem instanceof ElementWrapper && ((ElementWrapper) elem).getWrappedElement() instanceof SubClassedObject) {
			Object o = SubClassedObject.Helper.getProperty(((ElementWrapper) elem).getWrappedElement(), "TabIndex");
			return o == null ? 0 : (Integer) o;
		}
		Integer tabIndex = (Integer) PropertyHolder.get(elem).get("TabIndex");
		if (tabIndex == null)
			return 0;
		else
			return tabIndex;
	}
	
	@PatchMethod
	public static void setTabIndex(FocusImpl focusImpl, Element elem, int index) {
		if (elem instanceof ElementWrapper && ((ElementWrapper) elem).getWrappedElement() instanceof SubClassedObject) {
			SubClassedObject.Helper.setProperty(((ElementWrapper) elem).getWrappedElement(), "TabIndex", index);
		}
		else {
			PropertyHolder.get(elem).put("TabIndex", index);
		}
	}

}
