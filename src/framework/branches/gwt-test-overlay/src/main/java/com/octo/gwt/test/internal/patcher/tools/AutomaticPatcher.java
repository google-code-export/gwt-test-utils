package com.octo.gwt.test.internal.patcher.tools;

import java.lang.reflect.InvocationTargetException;
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

import com.octo.gwt.test.IPatcher;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class AutomaticPatcher implements IPatcher {

	public static final String INSERT_BEFORE = "INSERT_BEFORE";
	public static final String INSERT_AFTER = "INSERT_AFTER";

	private Map<Method, PatchMethod> annotatedMethods;

	private List<Method> processedMethods;

	private CtClass c;

	public void initClass(CtClass c) throws Exception {
		this.c = c;
		this.annotatedMethods = GwtTestReflectionUtils.getAnnotatedMethod(this.getClass(), PatchMethod.class);
		this.processedMethods = new ArrayList<Method>();
	}

	private Entry<Method, PatchMethod> findAnnotatedMethod(CtMethod m) throws Exception {
		for (Entry<Method, PatchMethod> entry : annotatedMethods.entrySet()) {
			String methodName = entry.getKey().getName();
			if (entry.getValue().methodName().length() > 0) {
				methodName = entry.getValue().methodName();
			}
			if (m.getName().equals(methodName)) {
				if (entry.getValue().args().length == 1 && entry.getValue().args()[0] == PatchMethod.class
				// Either this is a new method or it has to have a compatible signature
						&& (entry.getValue().value() != PatchType.STATIC_CALL || hasCompatibleSignature(m, entry.getKey()))) {
					return entry;
				} else {
					if (hasSameSignature(m, entry.getValue().args(), m.getParameterTypes())) {
						return entry;
					}
				}
			}
		}
		return null;
	}

	private boolean hasCompatibleSignature(CtMethod methodFound, Method methodAsked) throws Exception {
		CtClass[] classesFound = methodFound.getParameterTypes();
		Class<?>[] classesAsked = methodAsked.getParameterTypes();

		boolean compat = hasSameSignature(methodFound, classesAsked, classesFound);

		// account for the case where the method is non static in the original class
		// and we need to pass the object into the static patching method
		if (!compat && classesAsked.length >= 1 && inheritsFrom(classesAsked[0], methodFound.getDeclaringClass())) {
			Class<?>[] classesWithoutThis = new Class[classesAsked.length - 1];
			for (int i = 1; i < classesAsked.length; i++) {
				classesWithoutThis[i - 1] = classesAsked[i];
			}

			compat = hasSameSignature(methodFound, classesWithoutThis, classesFound);
		}

		return compat;
	}

	/**
	 * Checks to see if the checkCls inherits in some way from the superClass.
	 * i.e. does checkCls implement or extend superClass.
	 * 
	 * @param checkCls
	 * @param superClass
	 * @return
	 * @throws NotFoundException
	 */
	private boolean inheritsFrom(Class<?> checkCls, CtClass superClass) throws NotFoundException {
		List<CtClass> checked = new ArrayList<CtClass>();

		return inheritsFrom(checkCls, superClass, checked);
	}

	private boolean inheritsFrom(Class<?> checkCls, CtClass superClass, List<CtClass> checked) throws NotFoundException {
		if (checked.contains(superClass)) {
			return false;
		}

		checked.add(superClass);
		boolean inherits = checkCls.getName().equals(superClass.getName());

		if (!inherits) {
			for (CtClass intf : superClass.getInterfaces()) {
				inherits = inheritsFrom(checkCls, intf);
			}
		}

		if (!inherits && superClass.getSuperclass() != null) {
			inherits = inheritsFrom(checkCls, superClass.getSuperclass(), checked);
		}

		return inherits;
	}

	private boolean hasSameSignature(CtMethod m, Class<?>[] classesAsked, CtClass[] classesFound) throws Exception {
		if (classesAsked.length != classesFound.length) {
			return false;
		}
		for (int i = 0; i < classesAsked.length; i++) {
			CtClass foundClass = classesFound[i];
			String name;
			if (foundClass.isPrimitive()) {
				if (foundClass == CtClass.intType) {
					name = int.class.getName();
				} else if (foundClass == CtClass.booleanType) {
				    name = boolean.class.getName();
				} else if (foundClass == CtClass.shortType) {
				    name = short.class.getName();
				} else if (foundClass == CtClass.doubleType) {
				    name = double.class.getName();
				} else if (foundClass == CtClass.floatType) {
				    name = float.class.getName();
				} else if (foundClass == CtClass.charType) {
				    name = char.class.getName();
				} else if (foundClass == CtClass.byteType) {
				    name = byte.class.getName();
				} else {
					throw new RuntimeException("Not managed type " + foundClass + " for method " + m);
				}
			} else if (foundClass.isArray()) {
			    name = "[L" + foundClass.getComponentType().getName() + ";";
			} else {
			    name = foundClass.getName();
			}
			if (!name.equals(classesAsked[i].getName())) {
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
				newBody = INSERT_BEFORE + treatMethod(annotatedMethod);
				break;
			case INSERT_CODE_AFTER:
				newBody = INSERT_AFTER + treatMethod(annotatedMethod);
				break;
			case NEW_CODE_AS_STRING:
				newBody = treatMethod(annotatedMethod);
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

	private String treatMethod(Method annotatedMethod) throws IllegalAccessException, InvocationTargetException {
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
		return (String) annotatedMethod.invoke(null, params.toArray());
	}

	public void finalizeClass(CtClass c) throws Exception {
		for (Method m : annotatedMethods.keySet()) {
			if (!processedMethods.contains(m)) {
			    for (CtMethod cm : c.getMethods()) {
			        System.out.println(cm.getLongName());
			    }
			    System.out.println("Unused methods: " + (c.getMethods().length - processedMethods.size()));
				throw new RuntimeException("@PatchMethod not used : " + m);
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
