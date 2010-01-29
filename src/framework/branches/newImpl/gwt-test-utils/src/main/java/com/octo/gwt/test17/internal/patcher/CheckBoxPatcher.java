package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class CheckBoxPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element asOld(CheckBox checkBox, com.google.gwt.dom.client.Element elem) {
		return ElementUtils.castToUserElement(elem);
	}

}
