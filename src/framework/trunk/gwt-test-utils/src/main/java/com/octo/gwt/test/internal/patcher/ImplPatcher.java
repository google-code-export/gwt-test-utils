package com.octo.gwt.test.internal.patcher;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.gwt.core.client.impl.Impl;
import com.octo.gwt.test.PatchGwtConfig;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(Impl.class)
public class ImplPatcher extends AutomaticPatcher {

	@PatchMethod
	public static int getHashCode(Object o) {
		return HashCodeBuilder.reflectionHashCode(o);
	}

	@PatchMethod
	public static String getHostPageBaseURL() {
		return "http://127.0.0.1:8888/";
	}

	@PatchMethod
	public static String getModuleName() {
		return PatchGwtConfig.get().getModuleName();
	}

	@PatchMethod
	public static String getModuleBaseURL() {
		return getHostPageBaseURL() + getModuleName() + "/";
	}

}
