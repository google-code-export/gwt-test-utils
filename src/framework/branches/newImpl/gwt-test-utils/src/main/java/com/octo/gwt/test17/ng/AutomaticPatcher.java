package com.octo.gwt.test17.ng;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CtClass;
import javassist.CtMethod;

import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.internal.patcher.Patcher;

public class AutomaticPatcher implements Patcher {

	private Map<Method, PatchMethod> annotatedMethods;
	
	private List<Method> processedMethods;
	
	private CtClass c;
	
	public void initClass(CtClass c) throws Exception {
		this.c = c;
		this.annotatedMethods = ReflectionUtils.getAnnotatedMethod(this.getClass(), PatchMethod.class);
		this.processedMethods = new ArrayList<Method>();
	}

	private Entry<Method, PatchMethod> findAnnotatedMethod(CtMethod m) throws Exception {
		for(Entry<Method, PatchMethod> entry : annotatedMethods.entrySet()) {
			if (m.getName().equals(entry.getKey().getName())) {
				if (entry.getValue().args().length == 0) {
					return entry;
				}
				else {
					if (hasSameSignature(entry.getValue().args(), m.getParameterTypes())) {
						return entry;
					}
				}
			}
		}
		return null;
	}
	
	private boolean hasSameSignature(Class<?> [] classesAsked, CtClass [] classesFound) throws Exception {
		if (classesAsked.length != classesFound.length) {
			return false;
		}
		for(int i = 0; i < classesAsked.length; i ++) {
			Class<?> clazz = null;
			if (classesFound[i].isPrimitive()) {
				if (classesFound[i] == CtClass.intType) {
					clazz = Integer.class;
				}
				else {
					throw new RuntimeException("Not managed type " + classesFound[i]);
				}
			}
			else {
				clazz = Class.forName(classesFound[i].getName());
			}
			if (clazz != classesAsked[i]) {
				return false;
			}
		}
		return true;
	}
	
	public String getNewBody(CtMethod m) throws Exception {
		Entry<Method, PatchMethod> e = findAnnotatedMethod(m);
		if (e != null) {
			Method annotatedMethod = e.getKey();
			if (!Modifier.isStatic(annotatedMethod.getModifiers())) {
				throw new RuntimeException("Method " + annotatedMethod + " have to be static");
			}
			if (e.getValue().value() == PatchType.NEW_CODE_AS_STRING) {
				processedMethods.add(annotatedMethod);
				List<Object> params = new ArrayList<Object>();
				for(Class<?> clazz : annotatedMethod.getParameterTypes()) {
					if (clazz == CtClass.class) {
						params.add(c);
					}
					else {
						throw new RuntimeException("Not managed param " + clazz + " for method " + annotatedMethod);
					}
				}
				if (annotatedMethod.getReturnType() != String.class) {
					throw new RuntimeException("Wrong return type " + annotatedMethod.getReturnType() + " for method " + annotatedMethod);
				}
				return (String) annotatedMethod.invoke(null, params.toArray());
			}
			if (e.getValue().value() == PatchType.STATIC_CALL) {
				processedMethods.add(annotatedMethod);
				StringBuffer buffer = new StringBuffer();
				buffer.append("{");
				buffer.append("return ");
				buffer.append(this.getClass().getCanonicalName() + "." + annotatedMethod.getName());
				buffer.append("(");
				boolean append = false;
				if (!Modifier.isStatic(m.getModifiers())) {
					buffer.append("this");
					append = true;
				}
				for(int i = 0; i < m.getParameterTypes().length; i ++) {
					if (append) {
						buffer.append(", ");
					}
					int j = i + 1;
					buffer.append("$" + j);
					append = true;
				}
				buffer.append(");");
				buffer.append("}");
				return buffer.toString();
			}
		}
		return null;
	}

	public void finalizeClass() throws Exception {
		for(Method m : annotatedMethods.keySet()) {
			if (!processedMethods.contains(m)) {
				throw new RuntimeException("Not processed anotation " + m);
			}
		}
		
	}

}
