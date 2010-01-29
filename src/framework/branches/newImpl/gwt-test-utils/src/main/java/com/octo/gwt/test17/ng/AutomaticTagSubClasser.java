package com.octo.gwt.test17.ng;

import com.octo.gwt.test17.internal.overrides.TagAware;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

public class AutomaticTagSubClasser extends AutomaticElementSubclasser {

	private static final String OVERRIDE_TAG = "overrideTag";

	@Override
	public void createConstructor(ClassPool cp, CtClass subClazz, CtClass c) throws Exception {
		subClazz.addInterface(cp.get(TagAware.class.getCanonicalName()));
		
		CtField tagField = new CtField(cp.get(String.class.getCanonicalName()), OVERRIDE_TAG, subClazz);
		subClazz.addField(tagField);
		
		CtConstructor constructor = new CtConstructor(new CtClass[]{cp.get(String.class.getCanonicalName())}, subClazz);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{");
		stringBuffer.append("super();");
		stringBuffer.append("this." + OVERRIDE_TAG + " = $1;");
		stringBuffer.append("}");
		constructor.setBody(stringBuffer.toString());
		subClazz.addConstructor(constructor);
		
		CtMethod getTag = new CtMethod(cp.get(String.class.getCanonicalName()), "getTag", new CtClass[]{}, subClazz);
		getTag.setBody("return " + OVERRIDE_TAG + ";");
		subClazz.addMethod(getTag);
	}
	
}
