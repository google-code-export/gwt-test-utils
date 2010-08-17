package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.internal.patcher.tools.AutomaticElementSubclasser;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.SubClassedHelper;
import com.octo.gwt.test.internal.utils.ElementUtils;

@PatchClass(SelectElement.class)
public class SelectElementPatcher extends AutomaticElementSubclasser {

	private static final String SELECTED_INDEX_FIELD = "SelectedIndex";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);

		cons.insertAfter(SubClassedHelper.getCodeSetProperty("this", SELECTED_INDEX_FIELD, "-1", false) + ";");
	}

	@PatchMethod
	public static int getSize(SelectElement select) {
		int size = 0;

		for (int i = 0; i < select.getChildNodes().getLength(); i++) {
			Element e = ElementUtils.castToDomElement(select.getChildNodes().getItem(i));
			if (UIObject.isVisible(e)) {
				size++;
			}
		}

		return size;
	}

}
