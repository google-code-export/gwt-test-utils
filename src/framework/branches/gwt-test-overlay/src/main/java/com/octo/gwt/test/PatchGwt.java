package com.octo.gwt.test;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.internal.patcher.GWTPatcher;
import com.octo.gwt.test.internal.patcher.dom.JavaScriptObjectPatcher;
import com.octo.gwt.test.utils.PatchUtils;

public class PatchGwt {
	
	private static Locale LOCALE = null;
	
	private static Set<CtClass> overlaySet = new HashSet<CtClass>();
	
	private static ClassPool classPool;
	
	/**
	 * The new "super JavaScriptObject$ class", which implements all transformed overlay interface
	 */
	private static CtClass jsClass$;
	
	/**
	 * The original JavaScriptObject class transformed to an empty interface
	 */
	private static CtClass jsInterface;

	public static Locale getLocale() {
		return LOCALE;
	}
	
	public static void setClassPool(ClassPool classPool) {
		PatchGwt.classPool = classPool;
		GWTPatcher.classPool = classPool;
	}

	public static void setLocale(Locale locale) {
		LOCALE = locale;
	}
	
	public static boolean areAssertionEnabled() {
		boolean enabled = false;
		assert enabled = true;
		return enabled;
	}
	
	public static void patch(String className, IPatcher patcher) throws Exception {
		// get the original class
		InputStream is = classPool.getClassLoader().getResourceAsStream(className.replace('.', '/') + ".class");
		CtClass originClass = null;
		try {
			originClass = classPool.makeClass(is);
		} catch (Exception e) {
			throw new CannotCompileException(e);
		}

		// treat overlay type just like GWT HostedMode does
		if (isOverlay(originClass, overlaySet)) {
			CtClass newClass = classPool.getAndRename(className, className + "$");
			CtClass overlayInterface = classPool.makeInterface(className);
			jsClass$.addInterface(overlayInterface);
			overlaySet.add(overlayInterface);
			newClass.setSuperclass(jsClass$);
			PatchUtils.patch(classPool, newClass, patcher);
		} else {
			PatchUtils.patch(classPool, originClass, patcher);
		}
	}
	public static void patch(Class<?> clazz, IPatcher patcher) throws Exception {
		if (classPool == null) {
			throw new CannotCompileException("You have to setup a valid ClassPool throught PatchGwt.setClassPool(..)");
		}
		if (jsClass$ == null) {
			initAndPatchJavaScriptObjectClass();
		}
		
		String className = clazz.getCanonicalName();
		
		if (clazz.isMemberClass()) {
			int i = className.lastIndexOf(".");
			className = className.substring(0, i) + "$" + className.substring(i + 1);
		}
		
		patch(className, patcher);	
	}

	private static void initAndPatchJavaScriptObjectClass() throws NotFoundException, Exception {
		String jsClassName = JavaScriptObject.class.getCanonicalName();
		overlaySet.add(classPool.get(jsClassName));
		jsClass$ = classPool.getAndRename(jsClassName, jsClassName + "$");
		jsInterface = classPool.makeInterface(jsClassName);
		jsClass$.addInterface(jsInterface);
		PatchUtils.patch(classPool, jsClass$, new JavaScriptObjectPatcher());
	}
	
	private static boolean isOverlay(CtClass c, Set<CtClass> overlaySet) throws NotFoundException {
		for (CtClass overlay : overlaySet) {
			if (c.subtypeOf(overlay)) {
				return true;
			}
		}
		
		return false;
	}

}
