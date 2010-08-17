package com.octo.gwt.test.internal;

import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

import org.apache.log4j.Logger;

import com.octo.gwt.test.IPatcher;
import com.octo.gwt.test.utils.PatchGwtUtils;

public class GwtTranslator implements Translator {

	public static final Logger logger = Logger.getLogger(GwtTranslator.class);

	public void onLoad(ClassPool pool, String className) throws NotFoundException, CannotCompileException {
		try {
			IPatcher patcher = map.get(className);
			if (patcher != null) {
				logger.debug("Load class " + className + ", use patcher " + patcher.getClass().getCanonicalName());
				CtClass clazz = pool.get(className);
				logger.debug("Patch class " + className);
				PatchGwtUtils.patch(clazz, patcher);
				logger.debug("Class loaded & patched " + className);
			} else {
				logger.debug("Load class " + className + ", no patch");
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
