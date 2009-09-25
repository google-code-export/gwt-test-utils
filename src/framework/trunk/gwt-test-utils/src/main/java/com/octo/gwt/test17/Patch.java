package com.octo.gwt.test17;

public class Patch {

	public static final String INSERT_BEFORE = "INSERT_BEFORE ";

	public String methodName;

	public String code;

	public Boolean isNative;

	public Boolean isStatic;

	public Boolean isFinal;

	public Class<?>[] argsClasses;

	public Patch(String methodName, String code) {
		this(methodName, code, null);
	}
	
	public Patch(String methodName, String code, Class<?>[] argsClasses) {
		this.isFinal = null;
		this.isNative = null;
		this.isStatic = null;
		this.methodName = methodName;
		this.code = code;
		this.argsClasses = argsClasses;
	}

	public Patch setNative() {
		this.isNative = true;
		return this;
	}

	public Patch setStatic() {
		this.isStatic = true;
		return this;
	}

	public Patch setFinal() {
		this.isFinal = true;
		return this;
	}

	public Patch setArgClasses(Class<?>[] argsClasses) {
		this.argsClasses = argsClasses;
		return this;
	}
}
