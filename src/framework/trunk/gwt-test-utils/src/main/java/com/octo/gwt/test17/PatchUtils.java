package com.octo.gwt.test17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants.DefaultStringValue;

public class PatchUtils {

	private static final String REDEFINE_METHOD = "redefineClass";

	private static final String REDEFINE_CLASS = "com.octo.gwt.test17.bootstrap.Startup";

	private static final Pattern PROPERTIES_PATTERN = Pattern.compile("^([^#!].*)=(.*)$");
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
	 * Object used to try to create custom objects that normally would be 
	 * instanciate through the {@link GWT#create(Class)} method.
	 */
	public static InstanceCreator INSTANCE_CREATOR = null;

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

	public static Properties getProperties(String path) {
		String propertiesNameFile = "/" + path + ".properties";
		return loadProperties(path.getClass().getResourceAsStream(propertiesNameFile));

	}
	private static Properties loadProperties(InputStream resourceAsStream) {

		if (resourceAsStream == null) {
			return null;
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));		
			Properties prop = new Properties();
			String line;
			while ((line = br.readLine()) != null) {
				Matcher m = PROPERTIES_PATTERN.matcher(line);
				if (m.matches()) {
					prop.put(loadConvert(m.group(1).trim()), rencodeString(loadConvert(m.group(2).trim())));
				}
			}

			return prop;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		//		Properties temp = new Properties();
		//		try {
		//			temp.load(resourceAsStream);
		//			
		//			Properties prop = new Properties();
		//			for (Entry<Object, Object> entry : temp.entrySet()) {
		//				
		//				String key = new String (((String) entry.getKey()).getBytes("ISO-8859-1"), "UTF-8");
		//				String value = new String (((String) entry.getValue()).getBytes("ISO-8859-1"), "UTF-8");
		//				
		//				prop.put(key, value);
		//			}
		//			
		//			return prop;
		//		} catch (Exception e) {
		//			throw new RuntimeException(e);
		//		}
	}

	private static String loadConvert(String base) {
		int off = 0;
		int len = base.length();
		char[] in = base.toCharArray();

		char aChar;
		char[] out = new char[base.length()]; 
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];   
				if(aChar == 'u') {
					// Read the xxxx
					int value=0;
					for (int i=0; i<4; i++) {
						aChar = in[off++];  
						switch (aChar) {
						case '0': case '1': case '2': case '3': case '4':
						case '5': case '6': case '7': case '8': case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a': case 'b': case 'c':
						case 'd': case 'e': case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A': case 'B': case 'C':
						case 'D': case 'E': case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
							"Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char)value;
				} else {
					if (aChar == 't') aChar = '\t'; 
					else if (aChar == 'r') aChar = '\r';
					else if (aChar == 'n') aChar = '\n';
					else if (aChar == 'f') aChar = '\f'; 
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = (char)aChar;
			}
		}
		return new String (out, 0, outLen);
	}

	private static String rencodeString(String base) {
		List<Byte> bytes = new ArrayList<Byte>();
		byte[] baseBytes = base.getBytes();

		for (int i = 0; i < baseBytes.length; i++) {

			if (baseBytes[i] == (byte)-62 && i+1 < baseBytes.length && baseBytes[i+1] ==(byte)-96) {
				//cas "espace" lors d'un lancement des tests via eclipse
				for (byte b : " ".getBytes()) {
					bytes.add(b);
				}
				i++;
			} else if (baseBytes[i] == (byte)-96) {
				// cas "espace" lors d'un lancement des tests via console maven
				for (byte b : " ".getBytes()) {
					bytes.add(b);
				}
			} else {
				bytes.add(baseBytes[i]);
			}	
		}

		byte[] correct = new byte[bytes.size()];

		for (int i = 0; i < correct.length; i++) {
			correct[i] = bytes.get(i);
		}

		return new String(correct);
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
				throw new UnsupportedOperationException("No matching property \"" +  method.getName() + "\" for i18n class ["
						+ clazz.getCanonicalName() + "]. Please use the DefaultStringValue annotation");
			}

			return v.value();
		}
		if (method.getReturnType() == String.class) {
			return line;
		}
		String [] result = line.split(", ");
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

}
