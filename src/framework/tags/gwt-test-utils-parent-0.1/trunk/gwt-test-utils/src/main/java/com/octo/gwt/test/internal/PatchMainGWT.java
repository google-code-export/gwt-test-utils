package com.octo.gwt.test.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Hashtable;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.impl.CldrImpl;
import com.google.gwt.i18n.client.impl.CurrencyList;
import com.google.gwt.i18n.client.impl.LocaleInfoImpl;
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
import com.google.gwt.user.client.ui.impl.FocusImplOld;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.google.gwt.user.client.ui.impl.HyperlinkImpl;
import com.google.gwt.user.client.ui.impl.PopupImpl;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.google.gwt.user.datepicker.client.DateBox;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.IGWTLogHandler;
import com.octo.gwt.test.PatchConstants;
import com.octo.gwt.test.internal.dom.UserDomImpl;
import com.octo.gwt.test.internal.overrides.OverrideFormPanelImpl;
import com.octo.gwt.test.internal.overrides.OverrideImagePrototype;
import com.octo.gwt.test.internal.overrides.OverrideInputElement;
import com.octo.gwt.test.utils.PatchUtils;
import com.octo.gwt.test.utils.PatchUtils.BodyGetter;

@SuppressWarnings("deprecation")
public class PatchMainGWT {

	public static GwtCreateHandler gwtCreateHandler = null;

	public static IGWTLogHandler gwtLogHandler = null;

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

					if (methodName.equals("eventGetTarget")) {
						return "return null;";
					}

					if (methodName.equals("isOrHasChild")) {
						return "return " + PatchDOMImpl.class.getName() + ".isOrHasChild($1, $2);";
					}
					;

					return null;
				}
			});
		}

		if (classLiteral == DOMImpl.class) {
			return new UserDomImpl();
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
			return new OverrideFormPanelImpl();
		}

		if (classLiteral == DateBox.DefaultFormat.class) {
			return new DateBox.DefaultFormat();
		}

		if (classLiteral == HistoryImpl.class) {
			return PatchUtils.generateInstance(HistoryImpl.class.getCanonicalName(), new BodyGetter() {
				public String getBody(String methodName) {
					if (methodName.equals("init")) {
						return "return true;";
					}
					if (methodName.equals("nativeUpdate")) {
						return "return;";
					}
					return null;

				}
			});
		}
		if (classLiteral == CurrencyList.class) {
			return new CurrencyList();
		}
		if (Constants.class.isAssignableFrom(classLiteral)) {
			return generateConstantWrapper(classLiteral);
		}
		if (ImageBundle.class.isAssignableFrom(classLiteral)) {
			return generateImageWrapper(classLiteral);
		}

		if (classLiteral == OverrideDefaultImages.getDefaultImagesClass()) {
			return OverrideDefaultImages.getInstance();
		}

		if (classLiteral == MenuBar.Resources.class) {
			return new MenuBar.Resources() {

				public ImageResource menuBarSubMenuIcon() {
					return new ImageResourcePrototype(null, null, 0, 0, 0, 0, false);
				}
			};
		}

		if (classLiteral == ClippedImageImpl.class) {
			return new ClippedImageImpl();
		}

		Object o = createClass.get(classLiteral);

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
				throw new RuntimeException("Not managed return type for image bundle : " + method.getReturnType());
			}

		};
		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, ih);
	}

	private static class ConstantInvocationHandler implements InvocationHandler {

		private Class<?> wrappedClass;

		public ConstantInvocationHandler(Class<?> wrappedClass) {
			this.wrappedClass = wrappedClass;
		}

		public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
			return PatchUtils.extractFromPropertiesFile(wrappedClass, arg1);
		}

	}

	public static void log(String message, Throwable t) {
		if (gwtLogHandler != null) {
			gwtLogHandler.log(message, t);
		}
	}
}
