package com.octo.gwt.test17.internal;

import java.util.Hashtable;

import com.google.gwt.i18n.client.impl.CldrImpl;
import com.google.gwt.i18n.client.impl.LocaleInfoImpl;
import com.google.gwt.user.client.impl.DOMImpl;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.google.gwt.user.client.impl.WindowImpl;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.MenuBar.MenuBarImages;
import com.google.gwt.user.client.ui.UIObject.DebugIdImpl;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.google.gwt.user.client.ui.impl.FocusImplOld;
import com.google.gwt.user.client.ui.impl.HyperlinkImpl;
import com.octo.gwt.test17.GwtCreateHandler;
import com.octo.gwt.test17.PatchConstants;
import com.octo.gwt.test17.PatchUtils;
import com.octo.gwt.test17.internal.dom.UserDomImpl;
import com.octo.gwt.test17.overrides.OverrideInputElement;

public class PatchMainGWT {
	
	public static GwtCreateHandler gwtCreateHandler = null;
	
	public static Hashtable<Class<?>, Object> createClass = new Hashtable<Class<?>, Object>();
	

	public static Object create(Class<?> classLiteral) {
		if (classLiteral == DebugIdImpl.class) {
			return new UIObject.DebugIdImpl();
		}
		if (classLiteral == FocusImpl.class) {
			return new FocusImplOld();
		}
		if (classLiteral == WindowImpl.class) {
			return new WindowImpl();
		}

		if (classLiteral.getCanonicalName().equals(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME)) {
			return PatchUtils.generateInstance(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME, new PatchUtils.BodyGetter() {

				public String getBody(String methodName) {
					if (methodName.equals("createInputRadioElement")) {
						return "return new " + OverrideInputElement.class.getCanonicalName() + "($1);";
					}
					if (methodName.equals("eventPreventDefault")) {
						return "return;";
					}
					return null;
				}
			});
		}

		if (classLiteral == DOMImpl.class) {
			return new UserDomImpl();
		}
		
		if (classLiteral == LocaleInfoImpl.class) {
			return new LocaleInfoImpl();
		}
		
		if (classLiteral == CldrImpl.class) {
			return new CldrImpl();
		}
		
		if (classLiteral == HyperlinkImpl.class) {
			return new HyperlinkImpl();
		}
		
		if (classLiteral == HistoryImpl.class) {
			return new HistoryImpl();
		}
		
		if (classLiteral == MenuBarImages.class) {
			return new MenuBarImages() {
				
				public AbstractImagePrototype menuBarSubMenuIcon() {
					return null;
				}
			};
		}
		
		Object o = createClass.get(classLiteral);
		if (gwtCreateHandler != null) {
			o = gwtCreateHandler.create(classLiteral);
		}
		
		if (o == null) {
			throw new RuntimeException("Not managed create class for class : " + classLiteral.getCanonicalName());
		}
		return o;
	}

}
