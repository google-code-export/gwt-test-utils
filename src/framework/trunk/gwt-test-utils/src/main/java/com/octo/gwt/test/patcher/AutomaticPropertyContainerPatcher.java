package com.octo.gwt.test.patcher;

import java.lang.reflect.Modifier;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.PatchGwtClassPool;
import com.octo.gwt.test.internal.utils.PatchGwtUtils;

@PatchClass(Text.class)
public class AutomaticPropertyContainerPatcher extends AutomaticPatcher {

	private static final String PROPERTIES = "__PROPERTIES__";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);

		CtClass pcType = PatchGwtClassPool.get().get(PropertyContainer.class.getName());
		CtField field = new CtField(pcType, PROPERTIES, c);
		field.setModifiers(Modifier.PRIVATE);
		c.addField(field);

		c.addInterface(PatchGwtClassPool.get().get(PropertyContainerAware.class.getName()));

		CtMethod getProperties = new CtMethod(pcType, "getProperties", new CtClass[] {}, c);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{");
		stringBuffer.append("if (" + PROPERTIES + " == null) {");
		stringBuffer.append(PROPERTIES + " = new " + PropertyContainer.class.getName() + "();");
		stringBuffer.append("}");
		stringBuffer.append("return " + PROPERTIES + ";");
		stringBuffer.append("}");
		getProperties.setBody(stringBuffer.toString());
		c.addMethod(getProperties);

	}

	public String getNewBody(CtMethod m) throws Exception {
		String superNewBody = super.getNewBody(m);
		if (superNewBody != null) {
			return superNewBody;
		}
		if (Modifier.isNative(m.getModifiers())) {
			String fieldName = PatchGwtUtils.getPropertyName(m);
			if (fieldName != null) {
				if (m.getName().startsWith("set")) {
					return PropertyContainerHelper.getCodeSetProperty("this", fieldName, "$1", false);
				} else {
					return "return " + PropertyContainerHelper.getCodeGetProperty("this", fieldName, m.getReturnType());
				}
			}
		}
		return null;
	}

}
