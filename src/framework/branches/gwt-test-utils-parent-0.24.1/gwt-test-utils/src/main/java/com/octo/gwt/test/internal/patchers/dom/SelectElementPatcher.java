package com.octo.gwt.test.internal.patchers.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patchers.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(SelectElement.class)
public class SelectElementPatcher extends AutomaticPropertyContainerPatcher {

	private static final String SELECTED_INDEX_FIELD = "SelectedIndex";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);

		cons.insertAfter(PropertyContainerHelper.getCodeSetProperty("this", SELECTED_INDEX_FIELD, "-1") + ";");
	}

	@PatchMethod
	public static int getSize(SelectElement select) {
		int size = 0;

		for (int i = 0; i < select.getChildNodes().getLength(); i++) {
			Element e = select.getChildNodes().getItem(i).cast();
			if (UIObject.isVisible(e)) {
				size++;
			}
		}

		return size;
	}

}
