package com.octo.gwt.test;

import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

public class PatchConstructor implements Patch {

	private String code;
	private Class<?>[] argsClasses;

	public PatchConstructor(String code) {
		this(code, null);
	}

	public PatchConstructor(String code, Class<?>[] argsClasses) {
		this.code = code;
		this.argsClasses = argsClasses;
	}

	public void apply(CtClass classToPatch) throws Exception {
		CtConstructor c = findConstructor(classToPatch);
		classToPatch.removeConstructor(c);

		try {
			if (code == null) {
				c.setBody(null);
			} else {
				if (!code.endsWith(";")) {
					code += ";";
				}
				c.setBody("{" + code + "}");
			}
		} catch (CannotCompileException e) {
			throw new RuntimeException("Unable to compile code in class " + classToPatch.getName(), e);
		}

		classToPatch.addConstructor(c);

	}

	private CtConstructor findConstructor(CtClass ctClass) throws NotFoundException {
		List<CtConstructor> l = new ArrayList<CtConstructor>();

		for (CtConstructor c : ctClass.getDeclaredConstructors()) {
			if (argsClasses == null || argsClasses.length == c.getParameterTypes().length) {
				l.add(c);

				if (argsClasses != null) {
					int i = 0;
					for (Class<?> argClass : argsClasses) {
						if (!argClass.getName().equals(c.getParameterTypes()[i].getName())) {
							l.remove(c);
							continue;
						}
						i++;
					}
				}
			}
		}

		if (l.size() == 1) {
			return l.get(0);
		}
		if (l.size() == 0) {
			throw new RuntimeException("Unable to find a constructor with the specifed parameter types in class " + ctClass.getName());
		}
		throw new RuntimeException("Multiple constructor in class " + ctClass.getName() + ", you have to set parameter types discriminators");

	}
}
