package com.octo.gwt.test17;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class PatchMethod implements Patch {

	public String methodName, code;

	private Boolean isFinal, isNative, isStatic;

	private Class<?>[] argsClasses;

	public PatchMethod(String methodName, String code) {
		this(methodName, code, null);
	}

	public PatchMethod(String methodName, String code, Class<?>[] argsClasses) {
		this.isFinal = null;
		this.isNative = null;
		this.isStatic = null;
		this.methodName = methodName;
		this.code = code;
		this.argsClasses = argsClasses;
	}

	public PatchMethod setNative() {
		this.isNative = true;
		return this;
	}

	public PatchMethod setStatic() {
		this.isStatic = true;
		return this;
	}

	public PatchMethod setFinal() {
		this.isFinal = true;
		return this;
	}

	public PatchMethod setArgClasses(Class<?>[] argsClasses) {
		this.argsClasses = argsClasses;
		return this;
	}

	public void apply(CtClass classToPatch) throws Exception {
		CtMethod m = findMethod(classToPatch);
		classToPatch.removeMethod(m);
		if (Modifier.isNative(m.getModifiers())) {
			m.setModifiers(m.getModifiers() - Modifier.NATIVE);
		}

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
			throw new RuntimeException("Unable to compile code in class " + classToPatch.getName(), e);
		}

		classToPatch.addMethod(m);
	}

	/**
	 * Search method to patch in clazz
	 * 
	 * @param clazz
	 * @return
	 * @throws NotFoundException
	 */
	private CtMethod findMethod(CtClass clazz) throws NotFoundException {
		List<CtMethod> l = new ArrayList<CtMethod>();
		for (CtMethod m : clazz.getDeclaredMethods()) {
			if (!m.getName().equals(methodName)) {
				continue;
			}

			l.add(m);

			if ((argsClasses != null) && (m.getParameterTypes().length != argsClasses.length)) {
				l.remove(m);
				continue;
			}
			if ((isFinal != null) && (Modifier.isFinal(m.getModifiers()) != isFinal)) {
				l.remove(m);
				continue;
			}
			if ((isStatic != null) && (Modifier.isStatic(m.getModifiers()) != isStatic)) {
				l.remove(m);
				continue;
			}
			if ((isNative != null) && (Modifier.isNative(m.getModifiers()) != isNative)) {
				l.remove(m);
				continue;
			}

			if (argsClasses != null) {
				int i = 0;
				for (Class<?> argClass : argsClasses) {
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
			throw new RuntimeException("Unable to find " + methodName + " in class " + clazz.getName());
		}
		throw new RuntimeException("Multiple method " + methodName + " in class " + clazz.getName() + ", you have to set discriminators");
	}
}
