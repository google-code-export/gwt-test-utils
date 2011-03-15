package com.octo.gwt.test.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.gwt.test.Patcher;
import com.octo.gwt.test.internal.modifiers.JavaClassModifier;
import com.octo.gwt.test.internal.utils.GwtPatcherUtils;

public class GwtTranslator implements Translator {

	public static final Logger logger = LoggerFactory.getLogger(GwtTranslator.class);

	private static final Pattern TEST_PATTERN = Pattern.compile("^.*[T|t][E|e][S|s][T|t].*$");

	private Map<String, Patcher> map = new HashMap<String, Patcher>();

	public GwtTranslator(Map<String, Patcher> map) {
		this.map = map;
	}

	public void onLoad(ClassPool pool, String className) throws NotFoundException, CannotCompileException {
		try {
			Patcher patcher = map.get(className);
			if (patcher != null) {
				logger.debug("Load class " + className + ", use patcher " + patcher.getClass().getCanonicalName());
				CtClass clazz = pool.get(className);
				logger.debug("Patch class " + className);
				GwtPatcherUtils.patch(clazz, patcher);
				logger.debug("Class loaded & patched " + className);
			} else {
				logger.debug("Load class " + className + ", no patch");
			}

			modifiyClass(className);
		} catch (Exception e) {
			throw new CannotCompileException(e);
		}
	}

	public void start(ClassPool pool) throws NotFoundException, CannotCompileException {

	}

	private void modifiyClass(String className) throws Exception {

		for (String exclusion : ModuleData.get().getClientExclusions()) {
			if (className.equals(exclusion)) {
				// don't modify this class
				return;
			}
		}

		for (String clientPackage : ModuleData.get().getClientPaths()) {
			if (className.startsWith(clientPackage)) {
				// modifiy this class
				applyJavaClassModifier(className);
				return;
			}
		}

		if (TEST_PATTERN.matcher(className).matches()) {
			applyJavaClassModifier(className);
		}
	}

	private void applyJavaClassModifier(String className) throws Exception {
		logger.debug("Modify class " + className + ", with modifiers declared in 'META-INF/gwt-test-utils.properties'");
		CtClass classToModify = GwtClassPool.get().get(className);
		for (JavaClassModifier modifier : ConfigurationLoader.get().getJavaClassModifierList()) {
			modifier.modify(classToModify);
		}
	}

}
