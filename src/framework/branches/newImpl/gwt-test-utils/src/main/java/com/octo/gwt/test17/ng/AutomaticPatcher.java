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

	public String getNewBody(CtMethod m) throws Exception {
		for(Entry<Method, PatchMethod> entry : annotatedMethods.entrySet()) {
			if (m.getName().equals(entry.getKey().getName())) {
				if (!Modifier.isStatic(entry.getKey().getModifiers())) {
					throw new RuntimeException("Method " + entry.getKey() + " have to be static");
				}
				if (entry.getValue().value() == PatchType.NEW_CODE_AS_STRING) {
					processedMethods.add(entry.getKey());
					List<Object> params = new ArrayList<Object>();
					for(Class<?> clazz : entry.getKey().getParameterTypes()) {
						if (clazz == CtClass.class) {
							params.add(c);
						}
						else {
							throw new RuntimeException("Not managed param " + clazz + " for method " + entry.getKey());
						}
					}
					if (entry.getKey().getReturnType() != String.class) {
						throw new RuntimeException("Wrong return type " + entry.getKey().getReturnType() + " for method " + entry.getKey());
					}
					return (String) entry.getKey().invoke(null, params.toArray());
				}
				if (entry.getValue().value() == PatchType.STATIC_CALL) {
					processedMethods.add(entry.getKey());
					StringBuffer buffer = new StringBuffer();
					buffer.append("{");
					buffer.append("return ");
					buffer.append(this.getClass().getCanonicalName() + "." + entry.getKey().getName());
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
