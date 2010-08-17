package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.google.gwt.user.client.ui.impl.FormPanelImplHost;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.patcher.PropertyContainerHelper;

@PatchClass(FormPanelImpl.class)
public class FormPanelImplPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void hookEvents(FormPanelImpl panelImpl, Element iframe, Element form, FormPanelImplHost listener) {

	}

	@PatchMethod
	public static void setEncoding(FormPanelImpl panelImpl, Element form, String encoding) {
		PropertyContainerHelper.setProperty(form, "enctype", encoding);
		PropertyContainerHelper.setProperty(form, "encoding", encoding);
	}

	@PatchMethod
	public static String getEncoding(FormPanelImpl panelImpl, Element form) {
		return PropertyContainerHelper.getProperty(form, "enctype");
	}

	@PatchMethod
	public static String getContents(FormPanelImpl panelImpl, Element iframe) {
		return iframe.getInnerHTML();
	}

}
