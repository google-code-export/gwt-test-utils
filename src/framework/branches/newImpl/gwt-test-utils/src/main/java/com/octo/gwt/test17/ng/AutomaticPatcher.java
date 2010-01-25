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
	
	private CtClass c;
	
	public void initClass(CtClass c) throws Exception {
		this.c = c;
		this.annotatedMethods = ReflectionUtils.getAnnotatedMethod(this.getClass(), PatchMethod.class);
	}

	public String getNewBody(CtMethod m) throws Exception {
		for(Entry<Method, PatchMethod> entry : annotatedMethods.entrySet()) {
			if (m.getName().equals(entry.getKey().getName())) {
				if (!Modifier.isStatic(entry.getKey().getModifiers())) {
					throw new RuntimeException("Method " + entry.getKey() + " have to be static");
				}
				if (entry.getValue().value() == PatchType.NEW_CODE_AS_STRING) {
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
					StringBuffer buffer = new StringBuffer();
					buffer.append("{");
					buffer.append("return ");
					buffer.append(this.getClass().getCanonicalName() + "." + entry.getKey().getName());
					buffer.append("(");
					for(int i = 0; i < entry.getKey().getParameterTypes().length; i ++) {
						if (i > 0) {
							buffer.append(", ");
						}
						int j = i + 1;
						buffer.append("$" + j);
					}
					buffer.append(");");
					buffer.append("}");
					return buffer.toString();
				}
			}
		}
		return null;
	}

}
