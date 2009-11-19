package com.octo.gwt.test17;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.google.gwt.i18n.client.Constants.DefaultStringValue;

public class PatchUtils {

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

	private static final String REDEFINE_METHOD = "redefineClass";

	private static final String LOAD_PROPERTIES = "loadProperties";

	private static final String REDEFINE_CLASS = "com.octo.gwt.test17.bootstrap.Startup";

	private static final List<SequenceReplacement> sequenceReplacementList = new ArrayList<SequenceReplacement>();

	/**
	 * Classpool pour javassist
	 */
	private static ClassPool cp = ClassPool.getDefault();

	/**
	 * Method used to change bytecode of a class. Method is located in
	 * bootstrap.jar
	 */
	public static Method redefine;

	/**
	 * Method used to load properties file with charset. Method is located in
	 * bootstrap.jar
	 */
	public static Method loadProperties;

	/**
	 * Search method to patch in clazz
	 * 
	 * @param clazz
	 * @param p
	 * @return
	 * @throws NotFoundException
	 */
	private static CtMethod findMethod(CtClass clazz, Patch p) throws NotFoundException {
		List<CtMethod> l = new ArrayList<CtMethod>();
		for (CtMethod m : clazz.getDeclaredMethods()) {
			if (!m.getName().equals(p.methodName)) {
				continue;
			}

			l.add(m);

			if ((p.argsClasses != null) && (m.getParameterTypes().length != p.argsClasses.length)) {
				l.remove(m);
				continue;
			}
			if ((p.isFinal != null) && (Modifier.isFinal(m.getModifiers()) != p.isFinal)) {
				l.remove(m);
				continue;
			}
			if ((p.isStatic != null) && (Modifier.isStatic(m.getModifiers()) != p.isStatic)) {
				l.remove(m);
				continue;
			}
			if ((p.isNative != null) && (Modifier.isNative(m.getModifiers()) != p.isNative)) {
				l.remove(m);
				continue;
			}

			if (p.argsClasses != null) {
				int i = 0;
				for (Class<?> argClass : p.argsClasses) {
					if (!argClass.getName().equals(m.getParameterTypes()[i].getName())) {
						l.remove(m);
						continue;
					}
					i++;
				}
			}

		}
		if (l.size() == 1) {
			return l.get(0);
		}
		if (l.size() == 0) {
			throw new RuntimeException("Unable to find " + p.methodName + " in class " + clazz.getName());
		}
		throw new RuntimeException("Multiple method " + p.methodName + " in class " + clazz.getName() + ", you have to set discriminators");
	}

	/**
	 * Apply a list of patches on a clazz, and return new class bytecode
	 * 
	 * @param cp
	 * @param clazz
	 * @param list
	 * @throws Exception
	 */
	private static byte[] compilePatches(Class<?> clazz, Patch[] list) throws Exception {
		String className = clazz.getCanonicalName();
		if (clazz.isMemberClass()) {
			int k = className.lastIndexOf(".");
			className = className.substring(0, k) + "$" + className.substring(k + 1);
		}
		CtClass ctClazz = cp.get(className);
		for (Patch p : list) {
			CtMethod m = findMethod(ctClazz, p);
			ctClazz.removeMethod(m);
			if (Modifier.isNative(m.getModifiers())) {
				m.setModifiers(m.getModifiers() - Modifier.NATIVE);
			}
			String code = p.code;
			try {
				if (code.startsWith(Patch.INSERT_BEFORE)) {
					m.insertBefore(code.substring(Patch.INSERT_BEFORE.length()));
				} else {
					if (code.indexOf("return") == -1 && code.indexOf("throw") == -1) {
						code = "return " + code;
					}
					if (!code.endsWith(";")) {
						code += ";";
					}
					m.setBody("{" + code + "}");
				}
			} catch (CannotCompileException e) {
				System.err.println("Unable to compile code in class " + className);
				System.err.println(code);
				e.printStackTrace();
				throw new RuntimeException("Unable to compile code", e);
			}
			ctClazz.addMethod(m);
		}

		try {
			return ctClazz.toBytecode();
		} catch (Exception e) {
			System.err.println("Unable to compile code in class " + className);
			e.printStackTrace();
			throw new RuntimeException("Unable to compile code for class " + className, e);
		}
	}

	/**
	 * Replace class bytecode by another one
	 * 
	 * @param redefine
	 * @param clazz
	 * @param newByteCode
	 */
	private static void replaceClass(Class<?> clazz, byte[] newByteCode) {
		try {
			redefine.invoke(null, clazz, newByteCode);
		} catch (Exception e) {
			System.err.println("Unable to replace code in class " + clazz.getCanonicalName());
			e.printStackTrace();
			throw new RuntimeException("Unable to compile code for class " + clazz.getCanonicalName(), e);
		}
	}

	public static void applyPatches(Class<?> clazz, Patch[] list) throws Exception {
		replaceClass(clazz, compilePatches(clazz, list));
	}

	public static boolean areAssertionEnabled() {
		boolean enabled = false;
		assert enabled = true;
		return enabled;
	}

	public static void initRedefineMethod() throws Exception {
		Class<?> c = Class.forName(REDEFINE_CLASS);
		if (c == null) {
			throw new RuntimeException("No bootstrap class found");
		}
		PatchUtils.redefine = c.getMethod(REDEFINE_METHOD, Class.class, byte[].class);
		if (PatchUtils.redefine == null) {
			throw new RuntimeException("Method " + REDEFINE_METHOD + " not found in bootstrap class");
		}
	}

	public static void initLoadPropertiesMethod() throws Exception {
		Class<?> c = Class.forName(REDEFINE_CLASS);
		if (c == null) {
			throw new RuntimeException("No bootstrap class found");
		}
		PatchUtils.loadProperties = c.getMethod(LOAD_PROPERTIES, InputStream.class, String.class);
		if (PatchUtils.loadProperties == null) {
			throw new RuntimeException("Method " + LOAD_PROPERTIES + " not found in bootstrap class");
		}
	}

	public static Properties getProperties(String path) {
		String propertiesNameFile = "/" + path + ".properties";
		InputStream inputStream = path.getClass().getResourceAsStream(propertiesNameFile);
		if (inputStream == null) {
			return null;
		}
		try {
			Properties properties = (Properties) loadProperties.invoke(null, inputStream, "UTF-8");
			for (Entry<Object, Object> entry : properties.entrySet()) {
				for (SequenceReplacement strangeCharacterMapping : sequenceReplacementList) {
					entry.setValue(strangeCharacterMapping.treat((String) entry.getValue()));
				}
			}
			return properties;
		} catch (Exception e) {
			throw new RuntimeException("Unable to load property file" + path, e);
		}
	}

	public static Properties getLocalizedProperties(String prefix) throws IOException {
		Locale locale = PatchGWT.getLocale();
		if (locale == null) {
			throw new RuntimeException("No locale specified, please call PactchGWT.setLocale(...)");
		}
		String localeLanguage = PatchGWT.getLocale().getLanguage();
		return getProperties(prefix + "_" + localeLanguage);
	}

	public static Object extractFromPropertiesFile(Class<?> clazz, Method method) throws IOException {
		String line = null;
		Properties properties = getLocalizedProperties(clazz.getCanonicalName().replaceAll("\\.", "/"));
		if (properties != null) {
			line = properties.getProperty(method.getName());
		}
		if (line == null) {
			DefaultStringValue v = method.getAnnotation(DefaultStringValue.class);
			if (v == null) {
				throw new UnsupportedOperationException("No matching property \"" + method.getName() + "\" for i18n class ["
						+ clazz.getCanonicalName() + "]. Please use the DefaultStringValue annotation");
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

	public interface BodyGetter {
		String getBody(String methodName);
	}

	@SuppressWarnings("unchecked")
	public static <T> T generateInstance(String className, BodyGetter bodyGetter) {
		try {
			ClassPool cp = ClassPool.getDefault();
			CtClass c = cp.makeClass(className + "SubClass");
			CtClass superClazz = cp.get(className);
			c.setSuperclass(superClazz);
			CtConstructor constructor = new CtConstructor(new CtClass[] {}, c);
			constructor.setBody(";");
			c.addConstructor(constructor);
			for (CtMethod m : superClazz.getMethods()) {
				if ((m.getModifiers() & Modifier.ABSTRACT) == Modifier.ABSTRACT) {
					CtMethod mm = new CtMethod(m.getReturnType(), m.getName(), m.getParameterTypes(), c);
					mm.setModifiers(m.getModifiers() - Modifier.ABSTRACT);
					String body = "throw new UnsupportedOperationException(\"" + m.getName() + " on generated sub class of " + className + "\");";
					if (bodyGetter != null) {
						String b = bodyGetter.getBody(m.getName());
						if (b != null) {
							body = b;
						}
					}
					mm.setBody(body);
					c.addMethod(mm);
				}
			}
			return (T) c.toClass().newInstance();

		} catch (Exception e) {
			throw new RuntimeException("Unable to compile subclass of " + className, e);
		}
	}

	public static void clearSequenceReplacement() {
		sequenceReplacementList.clear();
	}

	public static void replaceSequenceInProperties(String regex, String to) {
		sequenceReplacementList.add(new SequenceReplacement(regex, to));
	}

}
