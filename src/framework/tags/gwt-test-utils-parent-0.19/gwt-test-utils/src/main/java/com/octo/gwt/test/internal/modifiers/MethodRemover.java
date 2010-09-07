package com.octo.gwt.test.internal.modifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class MethodRemover implements JavaClassModifier {

	private Map<String, List<RemovedMethod>> toRemoveByClass = new HashMap<String, List<RemovedMethod>>();

	public void removeMethod(String methodClass, String methodName, String methodSignature) {
		RemovedMethod m = new RemovedMethod(methodClass, methodName, methodSignature);
		List<RemovedMethod> methods = toRemoveByClass.get(methodClass);
		if (methods == null) {
			methods = new ArrayList<RemovedMethod>();
			toRemoveByClass.put(methodClass, methods);
		}
		methods.add(m);
	}

	public void modify(JavaClass classToModify) {
		if (toRemoveByClass.containsKey(classToModify.getClassName())) {
			List<RemovedMethod> toRemove = toRemoveByClass.get(classToModify.getClassName());
			Method[] methods = classToModify.getMethods();
			ArrayList<Method> methodList = new ArrayList<Method>();
			for (Method m : methods) {
				String mname = m.getName();
				String sig = m.getSignature();

				boolean add = true;
				for (RemovedMethod r : toRemove) {
					if (r.getMethodName().equals(mname) && r.getSignature().equals(sig)) {
						add = false;
						break;
					}
				}

				if (add) {
					methodList.add(m);
				}
			}
			classToModify.setMethods(methodList.toArray(new Method[0]));
		}
	}

	public static class RemovedMethod {
		private String className;
		private String methodName;
		private String signature;

		private RemovedMethod(String clsName, String methodName, String signature) {
			this.className = clsName;
			this.methodName = methodName;
			this.signature = signature;
		}

		public String getClassName() {
			return className;
		}

		public String getMethodName() {
			return methodName;
		}

		public String getSignature() {
			return signature;
		}
	}

}
