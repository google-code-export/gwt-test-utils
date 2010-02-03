package com.octo.gwt.test17.internal.patcher.tools;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.octo.gwt.test17.IPatcher;
import com.octo.gwt.test17.ReflectionUtils;

public class AutomaticPatcher implements IPatcher {

	public static final String INSERT_BEFORE = "INSERT_BEFORE";

	private Map<Method, PatchMethod> annotatedMethods;

	private List<Method> processedMethods;

	private CtClass c;

	public void initClass(CtClass c) throws Exception {
		this.c = c;
		this.annotatedMethods = ReflectionUtils.getAnnotatedMethod(this.getClass(), PatchMethod.class);
		this.processedMethods = new ArrayList<Method>();
	}

	private Entry<Method, PatchMethod> findAnnotatedMethod(CtMethod m) throws Exception {
		for (Entry<Method, PatchMethod> entry : annotatedMethods.entrySet()) {
			String methodName = entry.getKey().getName();
			if (entry.getValue().methodName().length() > 0) {
				methodName = entry.getValue().methodName();
			}
			if (m.getName().equals(methodName)) {
				if (entry.getValue().args().length == 1 && entry.getValue().args()[0] == PatchMethod.class) {
					return entry;
				} else {
					if (hasSameSignature(entry.getValue().args(), m.getParameterTypes())) {
						return entry;
					}
				}
			}
		}
		return null;
	}

	private boolean hasSameSignature(Class<?>[] classesAsked, CtClass[] classesFound) throws Exception {
		if (classesAsked.length != classesFound.length) {
			return false;
		}
		for (int i = 0; i < classesAsked.length; i++) {
			Class<?> clazz = null;
			if (classesFound[i].isPrimitive()) {
				if (classesFound[i] == CtClass.intType) {
					clazz = Integer.class;
				} else if (classesFound[i] == CtClass.booleanType) {
					clazz = Boolean.class;
				} else {
					throw new RuntimeException("Not managed type " + classesFound[i]);
				}
			} else {
				clazz = Class.forName(classesFound[i].getName());
			}
			if (clazz != classesAsked[i]) {
				return false;
			}
		}
		return true;
	}

	public String getNewBody(CtMethod m) throws Exception {
		String newBody = null;
		Entry<Method, PatchMethod> e = findAnnotatedMethod(m);
		if (e != null) {
			Method annotatedMethod = e.getKey();
			if (!Modifier.isStatic(annotatedMethod.getModifiers())) {
				throw new RuntimeException("Method " + annotatedMethod + " have to be static");
			}
			switch (e.getValue().value()) {
			case INSERT_CODE_BEFORE:
				newBody = INSERT_BEFORE;
			case NEW_CODE_AS_STRING:
				processedMethods.add(annotatedMethod);
				List<Object> params = new ArrayList<Object>();
				for (Class<?> clazz : annotatedMethod.getParameterTypes()) {
					if (clazz == CtClass.class) {
						params.add(c);
					} else {
						throw new RuntimeException("Not managed param " + clazz + " for method " + annotatedMethod);
					}
				}
				if (annotatedMethod.getReturnType() != String.class) {
					throw new RuntimeException("Wrong return type " + annotatedMethod.getReturnType() + " for method " + annotatedMethod);
				}
				String bodyPart = (String) annotatedMethod.invoke(null, params.toArray());
				newBody = (newBody != null) ? newBody + bodyPart : bodyPart;
				break;
			case STATIC_CALL:
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
				for (int i = 0; i < m.getParameterTypes().length; i++) {
					if (append) {
						buffer.append(", ");
					}
					int j = i + 1;
					buffer.append("$" + j);
					append = true;
				}
				buffer.append(");");
				buffer.append("}");
				newBody = buffer.toString();
				break;
			}
		}
		return newBody;
	}

	public void finalizeClass() throws Exception {
		for (Method m : annotatedMethods.keySet()) {
			if (!processedMethods.contains(m)) {
				throw new RuntimeException("Not processed anotation " + m);
			}
		}

	}

	protected CtConstructor findConstructor(CtClass ctClass, Class<?>... argsClasses) throws NotFoundException {
		List<CtConstructor> l = new ArrayList<CtConstructor>();

		for (CtConstructor c : ctClass.getDeclaredConstructors()) {
			if (argsClasses == null || argsClasses.length == c.getParameterTypes().length) {
				l.add(c);

				if (argsClasses != null) {
					int i = 0;
					for (Class<?> argClass : argsClasses) {
						if (!argClass.getName().equals(c.getParameterTypes()[i].getName())) {
							l.remove(c);
							continue;
						}
						i++;
					}
				}
			}
		}

		if (l.size() == 1) {
			return l.get(0);
		}
		if (l.size() == 0) {
			throw new RuntimeException("Unable to find a constructor with the specifed parameter types in class " + ctClass.getName());
		}
		throw new RuntimeException("Multiple constructor in class " + ctClass.getName() + ", you have to set parameter types discriminators");

	}

}
