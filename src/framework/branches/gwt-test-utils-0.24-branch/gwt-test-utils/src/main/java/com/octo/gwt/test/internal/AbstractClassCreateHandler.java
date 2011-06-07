package com.octo.gwt.test.internal;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.internal.utils.GwtPatcherUtils;

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

		ClassPool cp = PatchGwtClassPool.get();

		CtClass ctClass = PatchGwtClassPool.get().get(classLiteral.getName());
		CtClass subClass = cp.makeClass(classLiteral.getCanonicalName() + "SubClass");

		subClass.setSuperclass(ctClass);

		for (CtMethod m : ctClass.getDeclaredMethods()) {
			if (javassist.Modifier.isAbstract(m.getModifiers())) {
				CtMethod copy = new CtMethod(m, subClass, null);
				subClass.addMethod(copy);
			}
		}

		GwtPatcherUtils.patch(subClass, null);

		newClass = GwtTestClassLoader.getInstance().loadClass(subClass.getName());
		cache.put(classLiteral, newClass);

		return newClass.newInstance();
	}

}
