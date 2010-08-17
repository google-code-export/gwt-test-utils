package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.octo.gwt.test.internal.utils.ElementUtils;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(CheckBox.class)
public class CheckBoxPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element asOld(CheckBox checkBox, com.google.gwt.dom.client.Element elem) {
		return ElementUtils.castToUserElement(elem);
	}

}
