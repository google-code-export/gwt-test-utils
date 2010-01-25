package com.octo.gwt.test17.ng;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

import com.octo.gwt.test17.PatchUtils;
import com.octo.gwt.test17.ReflectionUtils;

public class AutomaticSubclasser extends AutomaticPatcher {

	private static final String PROPERTIES = "__PROPERTIES__";
	
	public static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
	
	private Class<?> subClazz;
	
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		ClassPool cp = c.getClassPool();
		CtClass subClass = cp.makeClass(c.getName() + "SubClassed");
		subClass.setSuperclass(c);
		subClass.addInterface(cp.get(SubClassedObject.class.getCanonicalName()));
		
		CtField field = new CtField(cp.get(PropertyContainer.class.getCanonicalName()), PROPERTIES, subClass);
		field.setModifiers(Modifier.PUBLIC);
		subClass.addField(field);
		
		CtConstructor constructor = new CtConstructor(new CtClass[]{}, subClass);
		StringBuffer cons = new StringBuffer();
		cons.append("{");
		cons.append("super();");
		cons.append(PROPERTIES + " = new " + PropertyContainer.class.getCanonicalName() + "();");
		cons.append("}");	
		constructor.setBody(cons.toString());
		subClass.addConstructor(constructor);
		
		subClazz = subClass.toClass();
		
		map.put(Class.forName(c.getName()), subClazz);
	}

	public String getNewBody(CtMethod m) throws Exception {
		String superNewBody = super.getNewBody(m);
		if (superNewBody != null) {
			return superNewBody;
		}
		if (Modifier.isNative(m.getModifiers())) {
			String fieldName = PatchUtils.getPropertyName(m);
			if (fieldName != null) {
				if (m.getName().startsWith("set")) {
					return generateSetter(fieldName);
				}
				else {
					return generateGetter(fieldName, m.getReturnType());
				}
			}
		}
		return null;
	}

	private String generateGetter(String fieldName, CtClass returnType) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append(subClazz.getCanonicalName() + " casted = (" + subClazz.getCanonicalName() + ") this;");
		if (returnType == CtClass.booleanType) {
			buffer.append("return casted." + PROPERTIES + ".getBoolean(\"" + fieldName + "\");");
		}
		else if (returnType == CtClass.intType) {
			buffer.append("return casted." + PROPERTIES + ".getInteger(\"" + fieldName + "\");");
		}
		else {
			buffer.append("return ($r) casted." + PROPERTIES + ".get(\"" + fieldName + "\");");
		}
		buffer.append("}");
		return buffer.toString();
	}

	private String generateSetter(String fieldName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(subClazz.getCanonicalName() + " casted = (" + subClazz.getCanonicalName() + ") this;");
		buffer.append("casted." + PROPERTIES + ".put(\"" + fieldName + "\", $1);");
		return buffer.toString();
	}
	
	public static void setProperty(Object o, String propertyName, Object propertyValue) {
		PropertyContainer propertyContainer = ReflectionUtils.getPrivateFieldValue(o, PROPERTIES);
		propertyContainer.put(propertyName, propertyValue);
	}
	
	public static Object getProperty(Object o, String propertyName) {
		PropertyContainer propertyContainer = ReflectionUtils.getPrivateFieldValue(o, PROPERTIES);
		return propertyContainer.get(propertyName);
	}

}
