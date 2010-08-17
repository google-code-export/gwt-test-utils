package com.octo.gwt.test.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import com.octo.gwt.test.GwtCreateHandler;

public class DefaultGwtCreateHandler implements GwtCreateHandler {

	public Object create(Class<?> classLiteral) throws Exception {
		if (classLiteral.isAnnotation() || classLiteral.isArray() || classLiteral.isEnum() || classLiteral.isInterface()
				|| Modifier.isAbstract(classLiteral.getModifiers())) {
			return null;
		}

		Constructor<?> defaultCons = getDefaultConstructor(classLiteral);

		if (defaultCons == null) {
			return null;
		} else {
			return defaultCons.newInstance();
		}
	}

	private Constructor<?> getDefaultConstructor(Class<?> classLiteral) {
		try {
			Constructor<?> cons = classLiteral.getDeclaredConstructor();
			cons.setAccessible(true);
			return cons;
		} catch (Exception e) {
			return null;
		}
	}

}
