package com.octo.gwt.test17.ng;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javassist.CtClass;
import javassist.CtMethod;

import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.internal.patcher.Patcher;

public class AutomaticPatcher implements Patcher {

	private List<Method> annotatedMethods;
	
	private CtClass c;
	
	public void initClass(CtClass c) throws Exception {
		this.c = c;
		this.annotatedMethods = ReflectionUtils.getAnnotatedMethod(this.getClass(), PatchMethod.class);
	}

	public String getNewBody(CtMethod m) throws Exception {
		for(Method annotatedMethod : annotatedMethods) {
			if (m.getName().equals(annotatedMethod.getName())) {
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
				if (!Modifier.isStatic(annotatedMethod.getModifiers())) {
					throw new RuntimeException("Method " + annotatedMethod + " have to be static");
				}
				return (String) annotatedMethod.invoke(null, params.toArray());
			}
		}
		return null;
	}

}
