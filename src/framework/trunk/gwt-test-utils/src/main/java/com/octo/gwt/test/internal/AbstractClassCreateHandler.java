package com.octo.gwt.test.internal;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;
import javassist.CtMethod;

import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.internal.utils.PatchGwtUtils;

public class AbstractClassCreateHandler implements GwtCreateHandler {

	private Map<Class<?>, Class<?>> cache = new HashMap<Class<?>, Class<?>>();

	// this GwtCreateHandler has been introduced to make possible the instanciation of abstract classes 
	// that gwt-test-utils doesn't patch right now
	public Object create(Class<?> classLiteral) throws Exception {
		if (classLiteral.isAnnotation() || classLiteral.isArray() || classLiteral.isEnum() || classLiteral.isInterface()
				|| !Modifier.isAbstract(classLiteral.getModifiers())) {
			return null;
		}

		Class<?> newClass = cache.get(classLiteral);

		if (newClass != null) {
			return newClass.newInstance();
		}

		CtClass ctClass = PatchGwtClassPool.getCtClass(classLiteral);
		CtClass subClass = PatchGwtClassPool.get().makeClass(classLiteral.getCanonicalName() + "SubClass");

		subClass.setSuperclass(ctClass);

		for (CtMethod m : ctClass.getDeclaredMethods()) {
			if (javassist.Modifier.isAbstract(m.getModifiers())) {
				CtMethod copy = new CtMethod(m, subClass, null);
				subClass.addMethod(copy);
			}
		}

		PatchGwtUtils.patch(subClass, null);

		newClass = subClass.toClass(GwtTestClassLoader.getInstance(), null);
		cache.put(classLiteral, newClass);

		return newClass.newInstance();
	}

}
