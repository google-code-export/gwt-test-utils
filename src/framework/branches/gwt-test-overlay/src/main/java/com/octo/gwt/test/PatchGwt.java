package com.octo.gwt.test;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.internal.patcher.dom.JavaScriptObjectPatcher;
import com.octo.gwt.test.utils.PatchGwtUtils;

public class PatchGwt {
	
	private static Locale LOCALE = null;
	
	private static Set<CtClass> overlaySet = new HashSet<CtClass>();
	
	/**
	 * The new "super JavaScriptObject$ class", which implements all transformed overlay interface
	 */
	private static CtClass jsClass$;
	
	/**
	 * The original JavaScriptObject class transformed to an empty interface
	 */
	private static CtClass jsInterface;

	private static Map<String, IPatcher> patchers = new LinkedHashMap<String, IPatcher>();
	
	public static Locale getLocale() {
		return LOCALE;
	}

	public static void setLocale(Locale locale) {
		LOCALE = locale;
	}
	
	public static boolean areAssertionEnabled() {
		boolean enabled = false;
		assert enabled = true;
		return enabled;
	}
	
	public static void addPatcher(String className, IPatcher patcher) {
	    patchers.put(className, patcher);
	}
	
	public static void patch() throws Exception  {
		ClassPool cp = PatchGwtClassPool.get();
		
	    // first, patch all the methods 
        for (Map.Entry<String, IPatcher> e : patchers.entrySet()) {
            String className = e.getKey();
            IPatcher patcher = e.getValue();

            CtClass cls = cp.get(className);
            
            PatchGwtUtils.patch(cls, patcher);
        }
        
        // second, rename all the classes with the fancy overlay mechanism
        for (String className : patchers.keySet()) {
            CtClass originClass = cp.get(className);

            // treat overlay type just like GWT HostedMode does
            if (isOverlay(originClass, overlaySet)) {
                CtClass newClass = cp.get(className);
                newClass.setName(className + "$");
                CtClass overlayInterface = cp.makeInterface(className);
                jsClass$.addInterface(overlayInterface);
                newClass.addInterface(overlayInterface);
                overlaySet.add(overlayInterface);
                newClass.setSuperclass(jsClass$);
            }
        }
	}
	
	public static void addPatcher(Class<?> clazz, IPatcher patcher) throws Exception {
		if (jsClass$ == null) {
			initAndPatchJavaScriptObjectClass();
		}
		
		String className = clazz.getCanonicalName();
		
		if (clazz.isMemberClass()) {
			int i = className.lastIndexOf(".");
			className = className.substring(0, i) + "$" + className.substring(i + 1);
		}
		
		addPatcher(className, patcher);	
	}

	private static void initAndPatchJavaScriptObjectClass() throws NotFoundException, Exception {
		ClassPool cp = PatchGwtClassPool.get();
		
		String jsClassName = JavaScriptObject.class.getCanonicalName();
		overlaySet.add(cp.get(jsClassName));
		jsClass$ = cp.getAndRename(jsClassName, jsClassName + "$");
		jsInterface = cp.makeInterface(jsClassName);
		jsClass$.addInterface(jsInterface);
		
		PatchGwtUtils.patch(jsClass$, new JavaScriptObjectPatcher());
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
