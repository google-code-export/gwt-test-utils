package com.octo.gwt.test;

import javassist.CannotCompileException;
import javassist.Loader;
import javassist.NotFoundException;

import com.octo.gwt.test.internal.ConfigurationLoader;
import com.octo.gwt.test.internal.GwtClassPool;
import com.octo.gwt.test.internal.GwtTranslator;

/**
 * <p>
 * gwt-test-utils custom {@link ClassLoader} used to load classes referenced
 * inside a test class extending {@link GwtTest}.
 * </p>
 * 
 * <p>
 * Its aim is to provide JVM-compliant versions of classes referenced in those
 * test classes. To obtain JVM-compliant code, the class loader relies on a set
 * of class {@link Patcher} which can be configured using the
 * <strong>META-INF\gwt-test-utils.properties</strong> file of your application.
 * </p>
 * 
 * @see GwtTranslator
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public class GwtClassLoader extends Loader {

	private static GwtClassLoader INSTANCE;

	/**
	 * Bind a static instance of the classloader to the current thread and
	 * return it.
	 * 
	 * @return the static classloader instance.
	 * 
	 * @see Thread#currentThread()
	 * @see Thread#setContextClassLoader(ClassLoader)
	 */
	public static GwtClassLoader get() {
		if (INSTANCE == null) {
			try {
				INSTANCE = new GwtClassLoader();
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

	/**
	 * Unbind the static classloader instance from the current thread by binding
	 * the system classloader instead.
	 */
	public static void reset() {
		Thread.currentThread().setContextClassLoader(INSTANCE.getParent());
	}

	private GwtTranslator translator;

	private GwtClassLoader() throws NotFoundException, CannotCompileException {
		super(GwtClassPool.get());

		ConfigurationLoader configurationLoader = ConfigurationLoader.createInstance(this.getParent());

		for (String s : configurationLoader.getDelegateList()) {
			delegateLoadingOf(s);
		}

		translator = new GwtTranslator(configurationLoader.getPatchers());

		addTranslator(GwtClassPool.get(), translator);
	}

}
