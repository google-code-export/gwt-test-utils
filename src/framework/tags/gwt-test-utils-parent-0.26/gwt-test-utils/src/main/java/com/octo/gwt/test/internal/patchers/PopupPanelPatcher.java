package com.octo.gwt.test.internal.patchers;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.user.client.ui.PopupPanel;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;

@PatchClass(PopupPanel.class)
public class PopupPanelPatcher extends AutomaticPatcher {

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);

		cons.insertAfter("getElement().getStyle().setProperty(\"visibility\", \"hidden\");");
	}
	//
	//	@PatchMethod
	//	public static Element getStyleElement(PopupPanel panel) {
	//		return ElementUtils.castToUserElement(panel.getElement());
	//	}

}
