package com.octo.gwt.test17.internal.patcher;

import java.util.ArrayList;
import java.util.List;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.octo.gwt.test17.PatchUtils;

public class AbstractPatcher implements Patcher {

	protected CtClass ctClass;

	public final void initClass(CtClass c) throws Exception {
		ctClass = c;
		initClass();
	}

	protected void initClass() throws Exception {
		//nothing to do by default
	}

	public String getNewBody(CtMethod m) {
		// nothing to patch by default
		return null;
	}

	protected static boolean match(CtMethod m, String regex) {
		return m.getName().matches(regex);
	}

	protected static boolean matchWithArgs(CtMethod m, String regex, Class<?>... argsClasses) {
		return match(m, regex) && argsMatch(m, argsClasses);
	}

	private static boolean argsMatch(CtMethod m, Class<?>... argsClasses) {
		try {
			return PatchUtils.matches(m.getParameterTypes(), argsClasses);
		} catch (NotFoundException e) {
			throw new RuntimeException("Error while getting \"" + m.getName() + "\" method parameter types", e);
		}
	}
	
	protected String callMethod(String staticMethodName) {
		return callMethod(staticMethodName, null);
	}

	protected String callMethod(String staticMethodName, String args) {
		return PatchUtils.callMethod(this.getClass(), staticMethodName, args);
	}

	protected CtConstructor findConstructor(Class<?>... argsClasses) throws NotFoundException {
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
