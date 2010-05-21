package com.octo.gwt.test.internal.patcher.tools;

import java.lang.reflect.Modifier;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

public class AutomaticSubclasser extends AutomaticGetAndSetPatcher {

	private static final String PROPERTIES = "__PROPERTIES__";

	public void createConstructor(ClassPool cp, CtClass subClazz, CtClass c) throws Exception {
		CtConstructor constructor = new CtConstructor(new CtClass[] {}, subClazz);
		StringBuffer cons = new StringBuffer();
		cons.append("{");
		cons.append("super();");
		cons.append("}");
		constructor.setBody(cons.toString());
		subClazz.addConstructor(constructor);
	}
	
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		ClassPool cp = c.getClassPool();
		CtClass subClazz = cp.makeClass(c.getName() + "SubClassed");
		subClazz.setSuperclass(c);
		subClazz.addInterface(cp.get(SubClassedObject.class.getCanonicalName()));

		CtField field = new CtField(cp.get(PropertyContainer.class.getCanonicalName()), PROPERTIES, subClazz);
		field.setModifiers(Modifier.PUBLIC);
		subClazz.addField(field);
		
		CtMethod getProperties = new CtMethod(cp.get(PropertyContainer.class.getCanonicalName()), "getOverrideProperties", new CtClass[] {}, subClazz);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{");
		stringBuffer.append("if (" + PROPERTIES + " == null) {");
		stringBuffer.append(PROPERTIES + " = new " + PropertyContainer.class.getCanonicalName() + "();");
		stringBuffer.append("}");
		stringBuffer.append("return " + PROPERTIES + ";");
		stringBuffer.append("}");
		getProperties.setBody(stringBuffer.toString());
		subClazz.addMethod(getProperties);
		
		createConstructor(cp, subClazz, c);

		Class<?> compiledSubClazz = subClazz.toClass();

		SubClassedHelper.register(Class.forName(c.getName()), compiledSubClazz);
	}

}
