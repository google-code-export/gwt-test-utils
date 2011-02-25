package com.octo.gwt.test;

import javassist.CannotCompileException;
import javassist.Loader;
import javassist.NotFoundException;

import com.octo.gwt.test.internal.ConfigurationLoader;
import com.octo.gwt.test.internal.GwtTranslator;
import com.octo.gwt.test.internal.PatchGwtClassPool;

/**
 * GwtTestClassLoader is the class loader used to instantiate classes referenced
 * inside a test class extending {@link AbstractGwtTest}.
 */
public class GwtTestClassLoader extends Loader {

	private static GwtTestClassLoader INSTANCE;

	public static void reset() {
		Thread.currentThread().setContextClassLoader(INSTANCE.getParent());
	}

	public static GwtTestClassLoader get() {
		if (INSTANCE == null) {
			try {
				INSTANCE = new GwtTestClassLoader();
			} catch (Exception e) {
				if (e instanceof RuntimeException) {
					throw (RuntimeException) e;
				} else {
					throw new RuntimeException(e);
				}
			}
		}

		Thread.currentThread().setContextClassLoader(INSTANCE);

		return INSTANCE;
	}

	private GwtTranslator translator;

	private GwtTestClassLoader() throws NotFoundException, CannotCompileException {
		super(PatchGwtClassPool.get());

		ConfigurationLoader configurationLoader = ConfigurationLoader.createInstance(this.getParent());

		for (String s : configurationLoader.getDelegateList()) {
			delegateLoadingOf(s);
		}

		translator = new GwtTranslator(configurationLoader.getPatchers());

		addTranslator(PatchGwtClassPool.get(), translator);
	}

}
