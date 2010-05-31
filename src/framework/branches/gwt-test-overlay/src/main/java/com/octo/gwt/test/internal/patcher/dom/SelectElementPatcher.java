package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.internal.patcher.tools.AutomaticGetAndSetPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.PropertyContainerAwareHelper;

public class SelectElementPatcher extends AutomaticGetAndSetPatcher {
	
	private static final String SELECTED_INDEX_FIELD = "SelectedIndex";
	
	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);
		
		cons.insertAfter(PropertyContainerAwareHelper.getCodeSetProperty("this", SELECTED_INDEX_FIELD, "-1") + ";");
	}

	@PatchMethod
	public static int getSize(SelectElement select) {
		int size = 0;

		for (int i = 0; i < select.getChildNodes().getLength(); i++) {
			Node n = select.getChildNodes().getItem(i);
			if (n instanceof Element) {
				if (UIObject.isVisible((Element) n)) {
					size++;
				}
			}
			
		}

		return size;
	}

}
