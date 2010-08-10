package com.octo.gwt.test.internal.patcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.CurrencyList;
import com.google.gwt.i18n.client.LocalizableResource;
import com.google.gwt.i18n.client.impl.CldrImpl;
import com.google.gwt.i18n.client.impl.LocaleInfoImpl;
import com.google.gwt.layout.client.LayoutImplIE8;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.user.client.impl.DOMImpl;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.google.gwt.user.client.impl.WindowImpl;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.OverrideDefaultImages;
import com.google.gwt.user.client.ui.TreeItem.TreeItemImpl;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.UIObject.DebugIdImpl;
import com.google.gwt.user.client.ui.impl.ClippedImageImpl;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.google.gwt.user.client.ui.impl.FocusImplStandard;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.google.gwt.user.client.ui.impl.HyperlinkImpl;
import com.google.gwt.user.client.ui.impl.PopupImpl;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.google.gwt.user.datepicker.client.DateBox;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.GwtLogHandler;
import com.octo.gwt.test.internal.overrides.OverrideImagePrototype;
import com.octo.gwt.test.internal.patcher.dom.DOMImplSubClassPatcher;
import com.octo.gwt.test.internal.patcher.dom.DOMImplUserSubClassPatcher;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.i18n.LocalizableResourceProxyFactory;
import com.octo.gwt.test.internal.patcher.tools.resources.ClientBundleProxyFactory;
import com.octo.gwt.test.utils.PatchGwtUtils;

@PatchClass(GWT.class)
@SuppressWarnings("deprecation")
public class GwtPatcher extends AutomaticPatcher {

	private static List<GwtCreateHandler> gwtCreateHandlers = new ArrayList<GwtCreateHandler>();
	public static GwtLogHandler gwtLogHandler = null;

	public static void reset() {
		gwtCreateHandlers.clear();
		gwtLogHandler = null;
	}

	public static boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		return gwtCreateHandlers.add(gwtCreateHandler);
	}

	@PatchMethod
	public static void log(String message, Throwable t) {
		if (gwtLogHandler != null) {
			gwtLogHandler.log(message, t);
		}
	}

	@PatchMethod
	public static String getVersion() {
		return "GWT 2 by GWT-test-utils";
	}

	@PatchMethod
	public static boolean isClient() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@PatchMethod
	public static Object create(Class<?> classLiteral) {
		if (classLiteral == DebugIdImpl.class) {
			return new UIObject.DebugIdImpl();
		}
		if (classLiteral == FocusImpl.class) {
			return new FocusImplStandard();
		}
		if (classLiteral == WindowImpl.class) {
			return new WindowImpl();
		}
		if (classLiteral.getCanonicalName().equals("com.google.gwt.dom.client.DOMImpl")) {
			return PatchGwtUtils.generateInstance("com.google.gwt.dom.client.DOMImpl", new DOMImplSubClassPatcher());
		}
		if (classLiteral == DOMImpl.class) {
			return PatchGwtUtils.generateInstance(DOMImpl.class.getCanonicalName(), new DOMImplUserSubClassPatcher());
		}
		if (classLiteral == HistoryImpl.class) {
			return new HistoryImpl();
		}
		if (classLiteral == PopupImpl.class) {
			return new PopupImpl();
		}
		if (classLiteral == LocaleInfoImpl.class) {
			return new LocaleInfoImpl();
		}
		if (classLiteral == HyperlinkImpl.class) {
			return new HyperlinkImpl();
		}
		if (classLiteral == CldrImpl.class) {
			return new CldrImpl();
		}
		if (classLiteral == WindowImpl.class) {
			return new WindowImpl();
		}
		if (classLiteral == TextBoxImpl.class) {
			return new TextBoxImpl();
		}
		if (classLiteral == FormPanelImpl.class) {
			return new FormPanelImpl();
		}
		if (classLiteral == CurrencyList.class) {
			return new CurrencyList();
		}
		if (classLiteral == DateBox.DefaultFormat.class) {
			return new DateBox.DefaultFormat();
		}
		if ("com.google.gwt.layout.client.LayoutImpl".equals(classLiteral.getName())) {
			return new LayoutImplIE8();
		}

		if (classLiteral == TreeItemImpl.class) {
			return new TreeItemImpl();
		}

		if (classLiteral == MenuBar.Resources.class) {
			return new MenuBar.Resources() {

				public ImageResource menuBarSubMenuIcon() {
					return new ImageResourcePrototype(null, null, 0, 0, 0, 0, false, false);
				}
			};
		}
		if (classLiteral == OverrideDefaultImages.getDefaultImagesClass()) {
			return OverrideDefaultImages.getInstance();
		}
		if (classLiteral == ClippedImageImpl.class) {
			return new ClippedImageImpl();
		}
		if (ImageBundle.class.isAssignableFrom(classLiteral)) {
			return generateImageWrapper(classLiteral);
		}

		if (LocalizableResource.class.isAssignableFrom(classLiteral)) {
			return LocalizableResourceProxyFactory.getFactory((Class<? extends LocalizableResource>) classLiteral).createProxy();
		}

		if (ClientBundle.class.isAssignableFrom(classLiteral)) {
			return ClientBundleProxyFactory.getFactory((Class<? extends ClientBundle>) classLiteral).createProxy();
		}

		for (GwtCreateHandler gwtCreateHandler : gwtCreateHandlers) {
			try {
				Object o = gwtCreateHandler.create(classLiteral);
				if (o != null) {
					return o;
				}
			} catch (Exception e) {
				throw new RuntimeException("Unable to create class " + classLiteral.getCanonicalName(), e);
			}
		}

		throw new RuntimeException("No mock registered for class : " + classLiteral.getCanonicalName());
	}

	private static Object generateImageWrapper(Class<?> clazz) {
		InvocationHandler ih = new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getReturnType() == AbstractImagePrototype.class) {
					return new OverrideImagePrototype();
				}
				throw new RuntimeException("Not managed return type for image bundle : " + method.getReturnType().getSimpleName());
			}

		};
		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, ih);
	}

}
