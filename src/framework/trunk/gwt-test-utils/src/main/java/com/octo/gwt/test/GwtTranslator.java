package com.octo.gwt.test;

import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

import com.octo.gwt.test.utils.PatchGwtUtils;

public class GwtTranslator implements Translator {

	public static boolean debug = "true".equals(System.getProperty(GwtTranslator.class.getCanonicalName() + ".debug"));

	private void log(String log) {
		if (debug) {
			System.err.println(log);
		}
	}

	public void onLoad(ClassPool pool, String className) throws NotFoundException, CannotCompileException {
		try {
			IPatcher patcher = map.get(className);
			if (patcher != null) {
				log("Load class " + className + ", use patcher " + patcher.getClass().getCanonicalName());
				CtClass clazz = pool.get(className);
				log("Patch class " + className);
				PatchGwtUtils.patch(clazz, patcher);
				log("Class loaded & patched " + className);
			} else {
				log("Load class " + className + ", no patch");
			}
		} catch (Exception e) {
			throw new CannotCompileException(e);
		}
	}

	public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
	}

	private Map<String, IPatcher> map = new HashMap<String, IPatcher>();

	public GwtTranslator(Map<String, IPatcher> map) {
		this.map = map;
	}

}
