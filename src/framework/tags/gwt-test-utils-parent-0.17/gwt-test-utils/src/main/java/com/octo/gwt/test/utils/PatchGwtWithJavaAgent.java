package com.octo.gwt.test.utils;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import javassist.CtClass;

import com.octo.gwt.test.IPatcher;
import com.octo.gwt.test.PatchGwtClassPool;

public class PatchGwtWithJavaAgent {

	private static final String REDEFINE_METHOD = "redefineClass";

	private static String BOOTSTRAP_CLASS = null;

	/**
	 * Method used to change bytecode of a class. Method is located in
	 * bootstrap.jar
	 */
	public static Method redefine;

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

	private static void initRedefineMethod() throws Exception {
		Class<?> c = Class.forName(BOOTSTRAP_CLASS);
		if (c == null) {
			throw new RuntimeException("No bootstrap class found");
		}
		redefine = c.getMethod(REDEFINE_METHOD, Class.class, byte[].class);
		if (redefine == null) {
			throw new RuntimeException("Method " + REDEFINE_METHOD + " not found in bootstrap class");
		}
	}

	public static void init() throws Exception {
		Properties properties = new Properties();
		InputStream inputStream = properties.getClass().getResourceAsStream("/META-INF/gwt-test-utils-bootstrap.properties");
		properties.load(inputStream);
		BOOTSTRAP_CLASS = properties.getProperty("className");
		try {
			Class.forName(BOOTSTRAP_CLASS);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to load " + BOOTSTRAP_CLASS + " class, you probably forgot to "
					+ "add the JVM parameter: -javaagent:target/bootstrap.jar");
		}
		initRedefineMethod();
	}

	public static void patch(Class<?> clazz, IPatcher patcher) throws Exception {
		String className = clazz.getCanonicalName();
		if (clazz.isMemberClass()) {
			int k = className.lastIndexOf(".");
			className = className.substring(0, k) + "$" + className.substring(k + 1);
		}

		CtClass c = PatchGwtClassPool.get().get(className);
		PatchGwtUtils.patch(c, patcher);
		replaceClass(clazz, c.toBytecode());
	}

}
