package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.google.gwt.user.client.ui.impl.FormPanelImplHost;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(FormPanelImpl.class)
public class FormPanelImplPatcher {
	
	@PatchMethod
	public static void hookEvents(FormPanelImpl panelImpl, Element iframe, Element form, FormPanelImplHost listener) {

	}
	
	@PatchMethod
	public static void unhookEvents(FormPanelImpl panelImpl, Element iframe, Element form) {
		
	}

	@PatchMethod
	public static void setEncoding(FormPanelImpl panelImpl, Element form, String encoding) {
		PropertyContainerHelper.setProperty(form, "enctype", encoding);
		PropertyContainerHelper.setProperty(form, "encoding", encoding);
	}

	@PatchMethod
	public static String getEncoding(FormPanelImpl panelImpl, Element form) {
		return PropertyContainerHelper.getObject(form, "enctype");
	}

	@PatchMethod
	public static String getContents(FormPanelImpl panelImpl, Element iframe) {
		return iframe.getInnerHTML();
	}

}
