package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.List;


import javassist.CannotCompileException;
import javassist.Loader;
import javassist.NotFoundException;

public class GwtTestClassLoader extends Loader {

	private static GwtTestClassLoader INSTANCE;

	public static ClassLoader getInstance() {
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

		return INSTANCE;
	}

	private GwtTranslator translator;

	private GwtTestClassLoader() throws NotFoundException, CannotCompileException {
		super(PatchGwtClassPool.get());

		init();

		ConfigurationLoader configurationLoader = ConfigurationLoader.createInstance(this.getParent());

		for (String s : configurationLoader.getDelegateList()) {
			delegateLoadingOf(s);
		}
		for (String s : configurationLoader.getNotDelegateList()) {
			notDelegateLoadingOf(s);
		}

		translator = new GwtTranslator(configurationLoader.getPatchers());

		addTranslator(PatchGwtClassPool.get(), translator);
	}

	private List<String> delegate;
	private List<String> delegatePackage;

	private List<String> notDelegate;
	private List<String> notDelegatePackage;

	private void init() {
		if (delegate == null) {
			delegate = new ArrayList<String>();
		}
		if (delegatePackage == null) {
			delegatePackage = new ArrayList<String>();
		}
		if (notDelegate == null) {
			notDelegate = new ArrayList<String>();
		}
		if (notDelegatePackage == null) {
			notDelegatePackage = new ArrayList<String>();
		}
	}

	@Override
	public void delegateLoadingOf(String classname) {
		init();
		if (classname.endsWith(".")) {
			delegatePackage.add(classname);
		} else {
			delegate.add(classname);
		}
	}

	public void notDelegateLoadingOf(String classname) {
		init();
		if (classname.endsWith(".")) {
			notDelegatePackage.add(classname);
		} else {
			notDelegate.add(classname);
		}
	}

	protected Class<?> loadClassByDelegation(String classname) throws ClassNotFoundException {
		if (isDelegated(classname)) {
			return delegateToParent(classname);
		}
		return null;
	}

	private boolean isDelegated(String classname) {
		if (notDelegate.contains(classname)) {
			return false;
		}
		if (delegate.contains(classname)) {
			return true;
		}
		for (String pkg : notDelegatePackage) {
			if (classname.startsWith(pkg)) {
				return false;
			}
		}
		for (String pkg : delegatePackage) {
			if (classname.startsWith(pkg)) {
				return true;
			}
		}
		return false;
	}

}
