package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class CheckBoxPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element asOld(com.google.gwt.dom.client.Element elem) {
		return ElementUtils.castToUserElement(elem);
	}

}
