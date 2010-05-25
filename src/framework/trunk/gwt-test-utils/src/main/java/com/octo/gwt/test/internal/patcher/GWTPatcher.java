package com.octo.gwt.test.internal.patcher;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.CurrencyList;
import com.google.gwt.i18n.client.impl.CldrImpl;
import com.google.gwt.i18n.client.impl.LocaleInfoImpl;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.resources.ext.DefaultExtensions;
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
import com.octo.gwt.test.IGWTLogHandler;
import com.octo.gwt.test.PatchConstants;
import com.octo.gwt.test.internal.overrides.OverrideImagePrototype;
import com.octo.gwt.test.internal.patcher.dom.DOMImplSubClassPatcher;
import com.octo.gwt.test.internal.patcher.dom.DOMImplUserSubClassPatcher;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.TextResourceReader;
import com.octo.gwt.test.utils.PatchUtils;

@SuppressWarnings("deprecation")
public class GWTPatcher extends AutomaticPatcher {

	public static GwtCreateHandler gwtCreateHandler = null;
	public static IGWTLogHandler gwtLogHandler = null;
	public static Map<Class<?>, Object> createClass = new HashMap<Class<?>, Object>();

	private static final Map<String, ClientBundleMethodsRegistry> registries = new HashMap<String, ClientBundleMethodsRegistry>();

	@PatchMethod(args = { String.class, Throwable.class })
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
		if (classLiteral.getCanonicalName().equals(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME)) {
			return PatchUtils.generateInstance(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME, new DOMImplSubClassPatcher());
		}
		if (classLiteral == DOMImpl.class) {
			return PatchUtils.generateInstance(DOMImpl.class.getCanonicalName(), new DOMImplUserSubClassPatcher());
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
			return generateClientBundleWrapper((Class<? extends ClientBundle>) classLiteral);
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
			return PatchUtils.extractFromPropertiesFile(wrappedClass, method);
		}

	}

	private static Object generateClientBundleWrapper(final Class<? extends ClientBundle> clazz) {
		final ClientBundleMethodsRegistry registry;

		if (registries.containsKey(clazz.getCanonicalName())) {
			registry = registries.get(clazz.getCanonicalName());
		} else {
			try {
				registry = new ClientBundleMethodsRegistry(clazz);
				registries.put(clazz.getCanonicalName(), registry);
			} catch (Exception e) {
				if (e instanceof RuntimeException) {
					throw (RuntimeException) e;
				} else {
					throw new RuntimeException("Error while getting resources informations of class [" + clazz.getCanonicalName() + "]", e);
				}
			}
		}

		InvocationHandler ih = new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				File resourceFile = registry.getResourceFile(method);
				if (method.getReturnType() == TextResource.class) {
					return generateTextResourceWrapper(method.getReturnType(), resourceFile, method.getName());
				}
				throw new RuntimeException("Not managed return type for ClientBundle : " + method.getReturnType().getSimpleName());
			}

		};
		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, ih);

	}

	private static Object generateTextResourceWrapper(Class<?> clazz, final File file, final String clientBundleFunctionName) {
		InvocationHandler ih = new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getName().equals("getText")) {
					return TextResourceReader.readFile(file);
				} else if (method.getName().equals("getName")) {
					return clientBundleFunctionName;
				}
				throw new RuntimeException("Not managed method \"" + method.getName() + "\" for generated TextResources proxy");
			}
		};
		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, ih);
	}

	private static class ClientBundleMethodsRegistry {

		private CtClass ctClass;
		private Map<String, File> resourceFiles = new HashMap<String, File>();

		public File getResourceFile(Method method) throws Exception {
			File file = resourceFiles.get(method.getName());
			if (file == null) {
				file = computeResourceFile(method);
				resourceFiles.put(method.getName(), file);
			}

			return file;
		}

		private File computeResourceFile(Method method) throws NotFoundException, URISyntaxException {
			List<String> filesSimpleNames = new ArrayList<String>();
			boolean computeExtensions = false;
			CtMethod m = ctClass.getDeclaredMethod(method.getName());
			MethodInfo minfo = m.getMethodInfo();
			AnnotationsAttribute attr = (AnnotationsAttribute) minfo.getAttribute(AnnotationsAttribute.invisibleTag);
			if (attr != null) {
				Annotation an = attr.getAnnotation(Source.class.getName());
				if (an != null) {
					MemberValue[] mvArray = ((ArrayMemberValue) an.getMemberValue("value")).getValue();
					if (mvArray != null) {
						for (MemberValue mv : mvArray) {
							StringMemberValue smv = (StringMemberValue) mv;
							filesSimpleNames.add(smv.getValue());
						}
					}
				}
			}
			
			if (filesSimpleNames.isEmpty()) {
				// no @Source annotation detected
				filesSimpleNames.add(method.getName());
				computeExtensions = true;
			}

				List<File> existingFiles = new ArrayList<File>();

				for (String fileSimpleName : filesSimpleNames) {
					String baseDir = ctClass.getPackageName().replaceAll("\\.", "/") + "/";
					String fileName = baseDir + fileSimpleName;

					if (computeExtensions) {
						String[] extensions = getResourceDefaultExtensions(method.getReturnType(), method);

						for (String extension : extensions) {
							String possibleFile = fileName + extension;
							URL url = GWTPatcher.class.getClassLoader().getResource(possibleFile);
							if (url != null) {
								existingFiles.add(new File(url.toURI()));
							}
						}
					} else {
						URL url = GWTPatcher.class.getClassLoader().getResource(fileName);
						if (url != null) {
							existingFiles.add(new File(url.toURI()));
						}
					}
				}

				if (existingFiles.isEmpty()) {
					throw new RuntimeException("No resource file found for method " + ctClass.getSimpleName() + "." + method.getName() + "()");
				} else if (existingFiles.size() > 1) {
					throw new RuntimeException("Too many resource files found for method " + ctClass.getSimpleName() + "." + method.getName() + "()");
				}

				return existingFiles.get(0);

		}

		public ClientBundleMethodsRegistry(Class<? extends ClientBundle> clazz) throws Exception {
			ctClass = ClassPool.getDefault().get(clazz.getName());
		}

		private String[] getResourceDefaultExtensions(Class<?> clazz, Method method) {
			DefaultExtensions annotation = method.getReturnType().getAnnotation(DefaultExtensions.class);
			if (annotation == null) {
				throw new RuntimeException(method.getReturnType().getSimpleName()
						+ " does not define a default extension for resource file. You should use a correct @" + Source.class.getSimpleName()
						+ " annotation on " + clazz.getSimpleName() + "." + method.getName() + "() method");
			} else {
				return annotation.value();
			}
		}

	}

}
