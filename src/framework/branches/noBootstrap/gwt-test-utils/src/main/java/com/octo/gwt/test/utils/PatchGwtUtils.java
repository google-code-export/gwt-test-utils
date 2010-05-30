package com.octo.gwt.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

import com.google.gwt.i18n.client.Constants.DefaultStringValue;
import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.IPatcher;
import com.octo.gwt.test.PatchGwtClassPool;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;

public class PatchGwtUtils {

	static class SequenceReplacement {

		private String regex;

		private String to;

		public String treat(String s) {
			return s.replaceAll(regex, to);
		}

		public SequenceReplacement(String regex, String to) {
			this.regex = regex;
			this.to = to;
		}

	}

	private static final List<SequenceReplacement> sequenceReplacementList = new ArrayList<SequenceReplacement>();

	/**
	 * Method used to load properties file with charset. Method is located in
	 * bootstrap.jar
	 */
	public static Method loadProperties;

	public static Locale locale;

	public static void initLoadPropertiesMethod() {
		loadProperties = GwtTestReflectionUtils.findMethod(Properties.class, "load", new Class[]{Reader.class});
	}

	public static Properties getProperties(String path) {
		if (loadProperties == null) {
			initLoadPropertiesMethod();
		}
		String propertiesNameFile = "/" + path + ".properties";
		InputStream inputStream = path.getClass().getResourceAsStream(
				propertiesNameFile);
		if (inputStream == null) {
			return null;
		}
		try {
			Properties properties = new Properties();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			loadProperties.invoke(properties, inputStreamReader);
			for (Entry<Object, Object> entry : properties.entrySet()) {
				for (SequenceReplacement strangeCharacterMapping : sequenceReplacementList) {
					entry.setValue(strangeCharacterMapping.treat((String) entry
							.getValue()));
				}
			}
			return properties;
		} catch (Exception e) {
			throw new RuntimeException("Unable to load property file" + path, e);
		}
	}

	public static Properties getLocalizedProperties(String prefix)
			throws IOException {
		if (locale == null) {
			throw new RuntimeException(
					"No locale specified, please call PatchGwtConfig.setLocale(...)");
		}
		String localeLanguage = locale.getLanguage();
		return getProperties(prefix + "_" + localeLanguage);
	}

	public static Object extractFromPropertiesFile(Class<?> clazz, Method method)
			throws IOException {
		String line = null;
		Properties properties = getLocalizedProperties(clazz.getCanonicalName()
				.replaceAll("\\.", "/"));
		if (properties != null) {
			line = properties.getProperty(method.getName());
		}
		if (line == null) {
			DefaultStringValue v = method
					.getAnnotation(DefaultStringValue.class);
			if (v == null) {
				throw new UnsupportedOperationException(
						"No matching property \""
								+ method.getName()
								+ "\" for i18n class ["
								+ clazz.getCanonicalName()
								+ "]. Please use the DefaultStringValue annotation");
			}

			String result = v.value();
			for (SequenceReplacement strangeCharacterMapping : sequenceReplacementList) {
				result = strangeCharacterMapping.treat(result);
			}
			return result;
		}
		if (method.getReturnType() == String.class) {
			return line;
		}
		String[] result = line.split(", ");
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T generateInstance(String className, IPatcher patcher) {
		try {
			CtClass c = PatchGwtClassPool.get().makeClass(className + "SubClass");
			CtClass superClazz = PatchGwtClassPool.get().get(className);

			c.setSuperclass(superClazz);
			CtConstructor constructor = new CtConstructor(new CtClass[] {}, c);
			constructor.setBody(";");
			c.addConstructor(constructor);

			for (CtMethod m : superClazz.getMethods()) {
				if (Modifier.isAbstract(m.getModifiers())) {
					CtMethod mm = new CtMethod(m.getReturnType(), m.getName(),
							m.getParameterTypes(), c);
					mm.setBody("{ throw new UnsupportedOperationException(\""
							+ m.getName() + " on generated sub class of "
							+ c.getName() + "\"); }");
					c.setModifiers(m.getModifiers() - Modifier.ABSTRACT);
					c.addMethod(mm);
				}
			}
			patch(c, patcher);
			return (T) c.toClass(GwtTestClassLoader.getInstance(), null).newInstance();

		} catch (Exception e) {
			throw new RuntimeException("Unable to compile subclass of "
					+ className, e);
		}
	}

	public static void reset() {
		sequenceReplacementList.clear();
		locale = null;
	}

	public static void replaceSequenceInProperties(String regex, String to) {
		sequenceReplacementList.add(new SequenceReplacement(regex, to));
	}

	public static void patch(CtClass c, IPatcher patcher) throws Exception {
		if (c == null) {
			throw new IllegalArgumentException(
					"the class to patch cannot be null");
		}

		if (patcher != null) {
			patcher.initClass(c);
		}

		for (CtMethod m : c.getDeclaredMethods()) {
			if (Modifier.isAbstract(m.getModifiers())) {
				// don't patch now
				continue;
			} else if (patcher != null) {
				String newBody = patcher.getNewBody(m);
				if (newBody != null) {
					if (newBody.startsWith(AutomaticPatcher.INSERT_BEFORE)) {
						PatchGwtUtils.insertBefore(m, newBody
								.substring(AutomaticPatcher.INSERT_BEFORE
										.length()));
					} else if (newBody
							.startsWith(AutomaticPatcher.INSERT_AFTER)) {
						PatchGwtUtils.insertAfter(m, newBody
								.substring(AutomaticPatcher.INSERT_AFTER
										.length()));
					} else {
						PatchGwtUtils.replaceImplementation(m, newBody);
					}
				}
			}
		}

		if (patcher != null) {
			patcher.finalizeClass();
		}
	}

	public static String getPropertyName(CtMethod m) throws Exception {
		String fieldName = null;
		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
			fieldName = m.getName().substring(3);
		} else if (m.getName().startsWith("is")
				&& m.getParameterTypes().length == 0) {
			fieldName = m.getName().substring(2);
		} else if (m.getName().startsWith("set")
				&& m.getParameterTypes().length == 1) {
			fieldName = m.getName().substring(3);
		}

		return fieldName;
	}

	private static void replaceImplementation(CtMethod m, String src)
			throws Exception {
		removeNativeModifier(m);

		if (src == null || src.trim().length() == 0) {
			m.setBody(null);
		} else {
			src = src.trim();
			if (!src.startsWith("{")) {
				if (!m.getReturnType().equals(CtClass.voidType)
						&& !src.startsWith("return")) {
					src = "{ return ($r)($w) " + src;
				} else {
					src = "{ " + src;
				}
			}

			if (!src.endsWith("}")) {
				if (!src.endsWith(";")) {
					src = src + "; }";
				} else {
					src = src + " }";
				}
			}
			try {
				m.setBody(src);
			} catch (CannotCompileException e) {
				throw new RuntimeException("Unable to compile body " + src, e);
			}
		}
	}

	private static void insertBefore(CtMethod m, String newBody)
			throws Exception {
		removeNativeModifier(m);
		m.insertBefore(newBody);
	}

	private static void insertAfter(CtMethod m, String newBody)
			throws Exception {
		removeNativeModifier(m);
		m.insertAfter(newBody);
	}

	private static void removeNativeModifier(CtMethod m) throws Exception {
		if (Modifier.isNative(m.getModifiers())) {
			m.setModifiers(m.getModifiers() - Modifier.NATIVE);
		}
	}
	
	public static boolean areAssertionEnabled() {
		boolean enabled = false;
		assert enabled = true;
		return enabled;
	}

}
