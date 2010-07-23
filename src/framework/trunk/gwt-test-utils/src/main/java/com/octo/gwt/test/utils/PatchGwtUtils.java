package com.octo.gwt.test.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.IPatcher;
import com.octo.gwt.test.PatchGwtClassPool;
import com.octo.gwt.test.PatchGwtConfig;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;

public class PatchGwtUtils {

	public static class SequenceReplacement {

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

	public static final List<SequenceReplacement> sequenceReplacementList = new ArrayList<SequenceReplacement>();

	private static Map<String, Properties> cachedProperties = new HashMap<String, Properties>();

	public static Properties getProperties(String path) {
		Properties properties = cachedProperties.get(path);

		if (properties != null) {
			return properties;
		}
		String propertiesNameFile = "/" + path + ".properties";
		InputStream inputStream = path.getClass().getResourceAsStream(propertiesNameFile);
		if (inputStream == null) {
			return null;
		}
		try {
			properties = new Properties();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			LoadPropertiesHelper.load(properties, inputStreamReader);
			cachedProperties.put(path, properties);
			return properties;
		} catch (Exception e) {
			throw new RuntimeException("Unable to load property file [" + path + "]", e);
		}
	}

	public static Properties getLocalizedProperties(String prefix, Locale locale) {
		if (locale == null) {
			throw new RuntimeException("Unable to determine a Locale to load file [" + prefix + "]. Please set one with "
					+ PatchGwtConfig.class.getSimpleName() + "setLocale(...)");
		}
		String localeLanguage = locale.getLanguage();
		return getProperties(prefix + "_" + localeLanguage);
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
					CtMethod mm = new CtMethod(m.getReturnType(), m.getName(), m.getParameterTypes(), c);
					mm.setBody("{ throw new UnsupportedOperationException(\"" + m.getName() + " on generated sub class of " + c.getName() + "\"); }");
					c.setModifiers(m.getModifiers() - Modifier.ABSTRACT);
					c.addMethod(mm);
				}
			}
			patch(c, patcher);
			return (T) c.toClass(GwtTestClassLoader.getInstance(), null).newInstance();

		} catch (Exception e) {
			throw new RuntimeException("Unable to compile subclass of " + className, e);
		}
	}

	public static void reset() {
		cachedProperties.clear();
		sequenceReplacementList.clear();
	}

	public static void patch(CtClass c, IPatcher patcher) throws Exception {
		if (c == null) {
			throw new IllegalArgumentException("the class to patch cannot be null");
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
						PatchGwtUtils.insertBefore(m, newBody.substring(AutomaticPatcher.INSERT_BEFORE.length()));
					} else if (newBody.startsWith(AutomaticPatcher.INSERT_AFTER)) {
						PatchGwtUtils.insertAfter(m, newBody.substring(AutomaticPatcher.INSERT_AFTER.length()));
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
		} else if (m.getName().startsWith("is") && m.getParameterTypes().length == 0) {
			fieldName = m.getName().substring(2);
		} else if (m.getName().startsWith("set") && m.getParameterTypes().length == 1) {
			fieldName = m.getName().substring(3);
		}

		return fieldName;
	}

	private static void replaceImplementation(CtMethod m, String src) throws Exception {
		removeNativeModifier(m);

		if (src == null || src.trim().length() == 0) {
			m.setBody(null);
		} else {
			src = src.trim();
			if (!src.startsWith("{")) {
				if (!m.getReturnType().equals(CtClass.voidType) && !src.startsWith("return")) {
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

	private static void insertBefore(CtMethod m, String newBody) throws Exception {
		removeNativeModifier(m);
		m.insertBefore(newBody);
	}

	private static void insertAfter(CtMethod m, String newBody) throws Exception {
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
