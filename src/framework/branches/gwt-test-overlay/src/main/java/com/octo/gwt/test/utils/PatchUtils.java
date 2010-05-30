package com.octo.gwt.test.utils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

import com.octo.gwt.test.IPatcher;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;

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

	private static final List<SequenceReplacement> sequenceReplacementList = new ArrayList<SequenceReplacement>();

	@SuppressWarnings("unchecked")
	public static <T> T generateInstance(ClassPool cp, String className, IPatcher patcher) {
		try {
			CtClass c = cp.makeClass(className + "SubClass");
			CtClass superClazz = cp.get(className);

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
			patch(cp, c, patcher);
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

	public static void patch(ClassPool cp, CtClass c, IPatcher patcher) throws Exception {
		if (c == null) {
			throw new IllegalArgumentException("The class to patch cannot be null");
		}
		
		if (patcher != null) {
			patcher.initClass(c, cp);
		}

		for (CtMethod m : c.getDeclaredMethods()) {
			if (Modifier.isAbstract(m.getModifiers())) {
				// don't patch now
				continue;
			} else if (patcher != null) {
				String newBody = patcher.getNewBody(m);
				if (newBody != null) {
					removeNativeModifier(m);
					if (newBody.startsWith(AutomaticPatcher.INSERT_BEFORE)) {
						m.insertBefore(newBody.substring(AutomaticPatcher.INSERT_BEFORE.length()));
					} else if (newBody.startsWith(AutomaticPatcher.INSERT_AFTER)) {
						m.insertAfter(newBody.substring(AutomaticPatcher.INSERT_AFTER.length()));
					} else {
						replaceImplementation(m, newBody);
					}
				}
			}
		}

		if (patcher != null) {
			patcher.finalizeClass(c, cp);
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
				throw new RuntimeException("Unable to compile " + m.getDeclaringClass().getName() + "." + m.getName() + "() body " + src, e);
			}
		}
	}

	private static void removeNativeModifier(CtMethod m) throws Exception {
		if (Modifier.isNative(m.getModifiers())) {
			m.setModifiers(m.getModifiers() - Modifier.NATIVE);
		}
	}
}
