package com.octo.gwt.test.internal.patcher.tools.resources;

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

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.ext.DefaultExtensions;
import com.octo.gwt.test.PatchGwtClassPool;
import com.octo.gwt.test.internal.patcher.GwtPatcher;

@SuppressWarnings("unchecked")
public class ClientBundleProxyFactory {

	private static Map<String, ClientBundleProxyFactory> factoryMap = new HashMap<String, ClientBundleProxyFactory>();

	private ClientBundleMethodsRegistry methodRegistry;
	private Class<? extends ClientBundle> proxiedClass;

	public static <T extends ClientBundle> ClientBundleProxyFactory getFactory(Class<T> clazz) {
		ClientBundleProxyFactory factory = factoryMap.get(clazz.getName());
		if (factory == null) {
			factory = new ClientBundleProxyFactory(clazz);
			factoryMap.put(clazz.getName(), factory);
		}

		return factory;
	}

	private ClientBundleProxyFactory(Class<? extends ClientBundle> proxiedClass) {
		this.proxiedClass = proxiedClass;
		this.methodRegistry = new ClientBundleMethodsRegistry(proxiedClass);
	}

	public <T extends ClientBundle> T createProxy() {
		InvocationHandler ih = new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				File resourceFile = methodRegistry.getResourceFile(method);
				if (method.getReturnType() == TextResource.class) {
					Class<? extends ClientBundle> clazz = (Class<ClientBundle>) method.getReturnType();
					return generateInvocationHandler(new TextResourceCallback(clazz, resourceFile), method.getName());
				} else if (CssResource.class.isAssignableFrom(method.getReturnType())) {
					Class<? extends ClientBundle> clazz = (Class<ClientBundle>) method.getReturnType();
					return generateInvocationHandler(new CssResourceCallback(clazz, resourceFile), method.getName());
				}
				throw new RuntimeException("Not managed return type for ClientBundle : " + method.getReturnType().getSimpleName());
			}

		};
		return (T) Proxy.newProxyInstance(proxiedClass.getClassLoader(), new Class<?>[] { proxiedClass }, ih);
	}

	private static Object generateInvocationHandler(final IClientBundleCallback callback, final String clientBundleFunctionName) {
		final Class<? extends ClientBundle> clazz = callback.getWrappedClass();
		InvocationHandler ih = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getName().equals("getName")) {
					return clientBundleFunctionName;
				} else {
					Object result = callback.call(proxy, method, args);
					if (result != null) {
						return result;
					}
				}
				throw new RuntimeException("Not managed method \"" + method.getName() + "\" for generated " + clazz.getSimpleName() + " proxy");
			}
		};

		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, ih);
	}

	protected class ClientBundleMethodsRegistry {

		private CtClass ctClass;
		private Map<String, File> resourceFiles = new HashMap<String, File>();

		public ClientBundleMethodsRegistry(Class<? extends ClientBundle> clazz) {
			try {
				ctClass = PatchGwtClassPool.get().get(clazz.getName());
			} catch (NotFoundException e) {
				throw new RuntimeException("Unable to find class [" + clazz.getName() + "]", e);
			}
		}

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
			MethodInfo minfo = m.getMethodInfo2();
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
				String fileName = (fileSimpleName.startsWith(baseDir)) ? fileSimpleName : baseDir + fileSimpleName;

				if (computeExtensions) {
					String[] extensions = getResourceDefaultExtensions(method.getReturnType(), method);

					for (String extension : extensions) {
						String possibleFile = fileName + extension;
						URL url = GwtPatcher.class.getClassLoader().getResource(possibleFile);
						if (url != null) {
							existingFiles.add(new File(url.toURI()));
						}
					}
				} else {
					URL url = GwtPatcher.class.getClassLoader().getResource(fileName);
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