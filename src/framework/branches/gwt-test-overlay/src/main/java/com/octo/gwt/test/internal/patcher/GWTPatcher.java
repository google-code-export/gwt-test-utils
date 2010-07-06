package com.octo.gwt.test.internal.patcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.CurrencyList;
import com.google.gwt.i18n.client.impl.CldrImpl;
import com.google.gwt.i18n.client.impl.LocaleInfoImpl;
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
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.resources.ClientBundleProxyFactory;
import com.octo.gwt.test.utils.PatchGwtUtils;

@SuppressWarnings("deprecation")
public class GWTPatcher extends AutomaticPatcher {

	public static GwtCreateHandler gwtCreateHandler = null;
	public static GwtLogHandler gwtLogHandler = null;
	public static Map<Class<?>, Object> classes = new HashMap<Class<?>, Object>();
    
	public static void reset() {
		classes.clear();
		gwtCreateHandler = null;
		gwtLogHandler = null;
	}
	
	@PatchMethod
	public static void log(String message, Throwable t) {
		if (gwtLogHandler != null) {
			gwtLogHandler.log(message, t);
		}
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
		if (Constants.class.isAssignableFrom(classLiteral)) {
			return generateConstantWrapper(classLiteral);
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

		if (ClientBundle.class.isAssignableFrom(classLiteral)) {
			return ClientBundleProxyFactory.getFactory((Class<? extends ClientBundle>) classLiteral).createProxy();
		}

		Object o = classes.get(classLiteral);

		if (o == null && gwtCreateHandler != null) {
			o = gwtCreateHandler.create(classLiteral);
		}

		if (o == null) {
			throw new RuntimeException("No mock registered for class : " + classLiteral.getCanonicalName());
		}
		return o;
	}

	private static Object generateConstantWrapper(Class<?> clazz) {
		InvocationHandler ih = new ConstantInvocationHandler(clazz);
		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, ih);
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

	private static class ConstantInvocationHandler implements InvocationHandler {

		private Class<?> wrappedClass;

		public ConstantInvocationHandler(Class<?> wrappedClass) {
			this.wrappedClass = wrappedClass;
		}

		public Object invoke(Object arg0, Method method, Object[] params) throws Throwable {
			return PatchGwtUtils.extractFromPropertiesFile(wrappedClass, method);
		}

	}
	
}