package com.octo.gwt.test.internal.modifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.CtClass;
import javassist.CtMethod;

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

	public void modify(CtClass classToModify) throws Exception {
		List<RemovedMethod> list = toRemoveByClass.get(classToModify.getName());
		if (list != null) {
			for (RemovedMethod r : list) {
				CtMethod toRemove = classToModify.getMethod(r.getMethodName(), r.getSignature());
				classToModify.removeMethod(toRemove);
			}
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
