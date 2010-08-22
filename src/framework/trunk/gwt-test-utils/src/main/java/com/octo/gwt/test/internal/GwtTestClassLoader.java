package com.octo.gwt.test.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.Loader;
import javassist.NotFoundException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import com.octo.gwt.test.internal.modifiers.JavaClassModifier;

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

	@Override
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

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classfile;
		try {

			translator.onLoad(PatchGwtClassPool.get(), name);

			try {
				classfile = PatchGwtClassPool.get().get(name).toBytecode();
			} catch (NotFoundException e) {
				return null;
			}
		} catch (Exception e) {
			throw new ClassNotFoundException("caught an exception while obtaining a class file for " + name, e);
		}

		int i = name.lastIndexOf('.');
		if (i != -1) {
			String pname = name.substring(0, i);
			if (getPackage(pname) == null)
				try {
					definePackage(pname, null, null, null, null, null, null, null);
				} catch (IllegalArgumentException e) {
					// ignore.  maybe the package object for the same
					// name has been created just right away.
				}
		}

		classfile = modifiyClass(classfile, name);

		return defineClass(name, classfile, 0, classfile.length);
	}

	private byte[] modifiyClass(byte[] classfile, String name) {

		ClassParser parser = new ClassParser(new ByteArrayInputStream(classfile), "");
		try {
			JavaClass cls = parser.parse();

			for (JavaClassModifier modifier : ConfigurationLoader.getInstance().getJavaClassModifierList()) {
				modifier.modify(cls);
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			cls.dump(out);

			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Error while modifying class '" + name + "'", e);
		}
	}

}
